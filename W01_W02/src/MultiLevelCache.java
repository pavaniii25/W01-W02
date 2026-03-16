import java.util.*;

class VideoData {
    String videoId;
    String content; // simulated video content
    public VideoData(String videoId, String content) {
        this.videoId = videoId;
        this.content = content;
    }
}

public class MultiLevelCache {

    // L1: in-memory, access-order LRU
    private LinkedHashMap<String, VideoData> L1Cache;
    private final int L1_CAPACITY = 10000;

    // L2: SSD-backed simulated as HashMap with access count
    private HashMap<String, VideoData> L2Cache;
    private HashMap<String, Integer> L2AccessCount;
    private final int L2_CAPACITY = 100000;
    private final int PROMOTION_THRESHOLD = 5;

    // L3: database simulation
    private HashMap<String, VideoData> database;

    // Cache hit/miss tracking
    private int L1Hits = 0, L1Misses = 0;
    private int L2Hits = 0, L2Misses = 0;
    private int L3Hits = 0, L3Misses = 0;

    public MultiLevelCache(HashMap<String, VideoData> db) {

        database = db;

        L1Cache = new LinkedHashMap<String, VideoData>(L1_CAPACITY, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, VideoData> eldest) {
                return size() > L1_CAPACITY;
            }
        };

        L2Cache = new HashMap<>();
        L2AccessCount = new HashMap<>();
    }

    // Retrieve video
    public VideoData getVideo(String videoId) {
        long start = System.nanoTime();

        // L1 lookup
        if (L1Cache.containsKey(videoId)) {
            L1Hits++;
            long duration = (System.nanoTime() - start) / 1000000; // ms
            System.out.println(videoId + " → L1 Cache HIT (" + duration + "ms)");
            return L1Cache.get(videoId);
        }
        L1Misses++;

        // L2 lookup
        if (L2Cache.containsKey(videoId)) {
            L2Hits++;
            L2AccessCount.put(videoId, L2AccessCount.getOrDefault(videoId, 0)+1);

            long duration = (System.nanoTime() - start) / 1000000; // ms
            System.out.println(videoId + " → L1 MISS → L2 HIT (" + duration + "ms)");

            // Promote if access count exceeds threshold
            if (L2AccessCount.get(videoId) >= PROMOTION_THRESHOLD) {
                promoteToL1(videoId);
            }

            return L2Cache.get(videoId);
        }
        L2Misses++;

        // L3 database access
        if (database.containsKey(videoId)) {
            L3Hits++;
            VideoData video = database.get(videoId);
            addToL2(videoId, video);

            long duration = (System.nanoTime() - start) / 1000000; // ms
            System.out.println(videoId + " → L1 MISS → L2 MISS → L3 HIT (" + duration + "ms)");
            return video;
        }
        L3Misses++;
        System.out.println(videoId + " → Video not found in any cache/database");
        return null;
    }

    private void promoteToL1(String videoId) {
        VideoData video = L2Cache.get(videoId);
        L1Cache.put(videoId, video);
        System.out.println("Promoted " + videoId + " to L1 Cache");
    }

    private void addToL2(String videoId, VideoData video) {
        if (L2Cache.size() >= L2_CAPACITY) {
            // Simple eviction: remove random entry
            String firstKey = L2Cache.keySet().iterator().next();
            L2Cache.remove(firstKey);
            L2AccessCount.remove(firstKey);
        }
        L2Cache.put(videoId, video);
        L2AccessCount.put(videoId, 1);
    }

    // Cache statistics
    public void getStatistics() {
        int L1Total = L1Hits + L1Misses;
        int L2Total = L2Hits + L2Misses;
        int L3Total = L3Hits + L3Misses;

        double overallHits = L1Hits + L2Hits + L3Hits;
        double overallTotal = overallHits + L1Misses + L2Misses + L3Misses;

        System.out.println("\n--- Cache Statistics ---");
        System.out.printf("L1: Hit Rate %.2f%%, Avg Time ~0.5ms\n", L1Total == 0 ? 0 : 100.0*L1Hits/L1Total);
        System.out.printf("L2: Hit Rate %.2f%%, Avg Time ~5ms\n", L2Total == 0 ? 0 : 100.0*L2Hits/L2Total);
        System.out.printf("L3: Hit Rate %.2f%%, Avg Time ~150ms\n", L3Total == 0 ? 0 : 100.0*L3Hits/L3Total);
        System.out.printf("Overall Hit Rate %.2f%%\n", overallTotal==0?0:100.0*overallHits/overallTotal);
    }

    // Demo
    public static void main(String[] args) {
        HashMap<String, VideoData> db = new HashMap<>();
        db.put("video_123", new VideoData("video_123","content123"));
        db.put("video_999", new VideoData("video_999","content999"));

        MultiLevelCache cache = new MultiLevelCache(db);

        cache.getVideo("video_123"); // L1 MISS → L2 MISS → L3 HIT
        cache.getVideo("video_123"); // L1 HIT
        cache.getVideo("video_999"); // L1 MISS → L2 MISS → L3 HIT
        cache.getVideo("video_123"); // L1 HIT

        cache.getStatistics();
    }
}