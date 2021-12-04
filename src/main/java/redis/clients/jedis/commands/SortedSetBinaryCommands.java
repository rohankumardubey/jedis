package redis.clients.jedis.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.params.ZAddParams;
import redis.clients.jedis.params.ZIncrByParams;
import redis.clients.jedis.params.ZParams;
import redis.clients.jedis.resps.ScanResult;
import redis.clients.jedis.resps.Tuple;

public interface SortedSetBinaryCommands {

  long zadd(byte[] key, double score, byte[] member);

  long zadd(byte[] key, double score, byte[] member, ZAddParams params);

  long zadd(byte[] key, Map<byte[], Double> scoreMembers);

  long zadd(byte[] key, Map<byte[], Double> scoreMembers, ZAddParams params);

  Double zaddIncr(byte[] key, double score, byte[] member, ZAddParams params);

  long zrem(byte[] key, byte[]... members);

  double zincrby(byte[] key, double increment, byte[] member);

  Double zincrby(byte[] key, double increment, byte[] member, ZIncrByParams params);

  Long zrank(byte[] key, byte[] member);

  Long zrevrank(byte[] key, byte[] member);

  List<byte[]> zrange(byte[] key, long start, long stop);

  List<byte[]> zrevrange(byte[] key, long start, long stop);

  List<Tuple> zrangeWithScores(byte[] key, long start, long stop);

  List<Tuple> zrevrangeWithScores(byte[] key, long start, long stop);

  byte[] zrandmember(byte[] key);

  List<byte[]> zrandmember(byte[] key, long count);

  List<Tuple> zrandmemberWithScores(byte[] key, long count);

  long zcard(byte[] key);

  Double zscore(byte[] key, byte[] member);

  List<Double> zmscore(byte[] key, byte[]... members);

  Tuple zpopmax(byte[] key);

  List<Tuple> zpopmax(byte[] key, int count);

  Tuple zpopmin(byte[] key);

  List<Tuple> zpopmin(byte[] key, int count);

  long zcount(byte[] key, double min, double max);

  long zcount(byte[] key, byte[] min, byte[] max);

  List<byte[]> zrangeByScore(byte[] key, double min, double max);

  List<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max);

  List<byte[]> zrevrangeByScore(byte[] key, double max, double min);

  List<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count);

  List<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min);

  List<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count);

  List<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count);

  List<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max);

  List<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min);

  List<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count);

  List<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min, int offset, int count);

  List<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max);

  List<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min);

  List<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max, int offset, int count);

  List<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset, int count);

  List<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min, int offset, int count);

  long zremrangeByRank(byte[] key, long start, long stop);

  long zremrangeByScore(byte[] key, double min, double max);

  long zremrangeByScore(byte[] key, byte[] min, byte[] max);

  long zlexcount(byte[] key, byte[] min, byte[] max);

  List<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max);

  List<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max, int offset, int count);

  List<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min);

  List<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min, int offset, int count);

  long zremrangeByLex(byte[] key, byte[] min, byte[] max);

  default ScanResult<Tuple> zscan(byte[] key, byte[] cursor) {
    return zscan(key, cursor, new ScanParams());
  }

  ScanResult<Tuple> zscan(byte[] key, byte[] cursor, ScanParams params);

  List<byte[]> bzpopmax(double timeout, byte[]... keys);

  List<byte[]> bzpopmin(double timeout, byte[]... keys);

  Set<byte[]> zdiff(byte[]... keys);

  Set<Tuple> zdiffWithScores(byte[]... keys);

  long zdiffStore(byte[] dstkey, byte[]... keys);

  Set<byte[]> zinter(ZParams params, byte[]... keys);

  Set<Tuple> zinterWithScores(ZParams params, byte[]... keys);

  long zinterstore(byte[] dstkey, byte[]... sets);

  long zinterstore(byte[] dstkey, ZParams params, byte[]... sets);

  Set<byte[]> zunion(ZParams params, byte[]... keys);

  Set<Tuple> zunionWithScores(ZParams params, byte[]... keys);

  long zunionstore(byte[] dstkey, byte[]... sets);

  long zunionstore(byte[] dstkey, ZParams params, byte[]... sets);

}
