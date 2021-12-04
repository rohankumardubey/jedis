package redis.clients.jedis.benchmark;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Calendar;
import redis.clients.jedis.DefaultJedisClientConfig;

import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.Jedis;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.Connection;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.HostAndPortUtil;

public class GetSetBenchmark {
  private static HostAndPort hnp = HostAndPortUtil.getRedisServers().get(0);
  private static final int TOTAL_OPERATIONS = 100000;

  public static void main(String[] args) throws UnknownHostException, IOException {
//    Jedis jedis = new Jedis(hnp);
//    jedis.connect();
//    jedis.auth("foobared");
//    jedis.flushAll();
    Connection conn = new Connection(hnp, DefaultJedisClientConfig.builder().password("foobared").build());
    conn.executeCommand(Protocol.Command.FLUSHALL);
    UnifiedJedis jedis = new UnifiedJedis(conn);

    long begin = Calendar.getInstance().getTimeInMillis();

    for (int n = 0; n <= TOTAL_OPERATIONS; n++) {
      String key = "foo" + n;
      jedis.set(key, "bar" + n);
      jedis.get(key);
    }

    long elapsed = Calendar.getInstance().getTimeInMillis() - begin;

//    jedis.disconnect();
    conn.close();

    System.out.println(((1000 * 2 * TOTAL_OPERATIONS) / elapsed) + " ops");
  }
}