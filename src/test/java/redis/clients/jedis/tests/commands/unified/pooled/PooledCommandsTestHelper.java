package redis.clients.jedis.tests.commands.unified.pooled;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.tests.HostAndPortUtil;

public class PooledCommandsTestHelper {

  private static final HostAndPort nodeInfo = HostAndPortUtil.getRedisServers().get(0);

  private static Jedis node;

  static JedisPooled getPooled() throws InterruptedException {

    node = new Jedis(nodeInfo);
    node.auth("foobared");
    node.flushAll();

    return new JedisPooled(nodeInfo.getHost(), nodeInfo.getPort(), null, "foobared");
  }

  static void clearData() {
    node.flushDB();
  }
}