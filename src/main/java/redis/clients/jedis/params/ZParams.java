package redis.clients.jedis.params;

import static redis.clients.jedis.Protocol.Keyword.AGGREGATE;
import static redis.clients.jedis.Protocol.Keyword.WEIGHTS;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.CommandArguments;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.util.SafeEncoder;

public class ZParams implements IParams {

  public enum Aggregate {
    SUM, MIN, MAX;

    /**
     * @deprecated This will be private in future. Use {@link #getRaw()}.
     */
    @Deprecated
    public final byte[] raw;

    Aggregate() {
      raw = SafeEncoder.encode(name());
    }

    public byte[] getRaw() {
      return raw;
    }
  }

  private final List<byte[]> params = new ArrayList<>();

  /**
   * Set weights.
   * @param weights weights.
   * @return
   */
  public ZParams weights(final double... weights) {
    params.add(WEIGHTS.getRaw());
    for (final double weight : weights) {
      params.add(Protocol.toByteArray(weight));
    }

    return this;
  }

  public ZParams aggregate(final Aggregate aggregate) {
    params.add(AGGREGATE.getRaw());
    params.add(aggregate.raw);
    return this;
  }

  @Override
  public void addParams(CommandArguments args) {
    params.forEach(param -> args.add(param));
  }
}
