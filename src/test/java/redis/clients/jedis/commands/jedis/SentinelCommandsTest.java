package redis.clients.jedis.commands.jedis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Sentinel;
import redis.clients.jedis.HostAndPortUtil;

public class SentinelCommandsTest {

  protected static HostAndPort master2 = HostAndPortUtil.getRedisServers().get(2);
  protected static HostAndPort replica2 = HostAndPortUtil.getRedisServers().get(3);

  protected static HostAndPort sentinel2_1 = HostAndPortUtil.getSentinelServers().get(1);
  protected static HostAndPort sentinel2_2 = HostAndPortUtil.getSentinelServers().get(3);

  @Test
  public void myIdSentinels() {
    String id1;
    try (Sentinel sentinel = new Sentinel(sentinel2_1)) {
      id1 = sentinel.sentinelMyId();
      assertTrue(id1.matches("[0-9a-f]+"));
    }

    try (Sentinel sentinel2 = new Sentinel(sentinel2_2)) {
      Map<String, String> details1 = sentinel2.sentinelSentinels("mymaster").get(0);
      assertEquals(id1, details1.get("runid"));
    }
  }

  @Test
  public void masterMasters() {
    String runId;
    try (Sentinel sentinel = new Sentinel(sentinel2_1)) {
      Map<String, String> details = sentinel.sentinelMaster("mymaster");
      assertEquals("mymaster", details.get("name"));
      runId = details.get("runid");
    }

    try (Sentinel sentinel2 = new Sentinel(sentinel2_2)) {
      Map<String, String> details = sentinel2.sentinelMasters().get(0);
      assertEquals("mymaster", details.get("name"));
      assertEquals(runId, details.get("runid"));
    }
  }

  @Test
  public void replicas() {
    try (Sentinel sentinel = new Sentinel(sentinel2_1)) {
      Map<String, String> details = sentinel.sentinelReplicas("mymaster").get(0);
      assertEquals(Integer.toString(replica2.getPort()), details.get("port"));
    }
  }
}
