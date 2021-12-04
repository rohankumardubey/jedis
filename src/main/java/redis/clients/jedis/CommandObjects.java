package redis.clients.jedis;

import static redis.clients.jedis.Protocol.Command.*;
import static redis.clients.jedis.Protocol.Keyword.*;

import com.google.gson.Gson;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;

import redis.clients.jedis.Protocol.Command;
import redis.clients.jedis.Protocol.Keyword;
import redis.clients.jedis.args.*;
import redis.clients.jedis.commands.ProtocolCommand;
import redis.clients.jedis.json.JsonProtocol.JsonCommand;
import redis.clients.jedis.json.JsonSetParams;
import redis.clients.jedis.json.Path;
import redis.clients.jedis.json.Path2;
import redis.clients.jedis.params.*;
import redis.clients.jedis.resps.*;
import redis.clients.jedis.search.*;
import redis.clients.jedis.search.SearchProtocol.SearchCommand;
import redis.clients.jedis.search.SearchProtocol.SearchKeyword;
import redis.clients.jedis.search.SearchResult.SearchResultBuilder;
import redis.clients.jedis.search.aggr.AggregationBuilder;
import redis.clients.jedis.search.aggr.AggregationResult;
import redis.clients.jedis.stream.*;

public class CommandObjects {

  protected CommandArguments commandArguments(ProtocolCommand command) {
    return new CommandArguments(command);
  }

  // Key commands
  public final CommandObject<Boolean> exists(String key) {
    return new CommandObject<>(commandArguments(Command.EXISTS).key(key), BuilderFactory.BOOLEAN);
  }

  public final CommandObject<Long> exists(String... keys) {
    return new CommandObject<>(commandArguments(Command.EXISTS).keys((Object[]) keys), BuilderFactory.LONG);
  }

  public final CommandObject<Boolean> exists(byte[] key) {
    return new CommandObject<>(commandArguments(Command.EXISTS).key(key), BuilderFactory.BOOLEAN);
  }

  public final CommandObject<Long> exists(byte[]... keys) {
    return new CommandObject<>(commandArguments(Command.EXISTS).keys((Object[]) keys), BuilderFactory.LONG);
  }

  public final CommandObject<Long> persist(String key) {
    return new CommandObject<>(commandArguments(PERSIST).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> persist(byte[] key) {
    return new CommandObject<>(commandArguments(PERSIST).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<String> type(String key) {
    return new CommandObject<>(commandArguments(Command.TYPE).key(key), BuilderFactory.STRING);
  }

  public final CommandObject<String> type(byte[] key) {
    return new CommandObject<>(commandArguments(Command.TYPE).key(key), BuilderFactory.STRING);
  }

  public final CommandObject<byte[]> dump(String key) {
    return new CommandObject<>(commandArguments(DUMP).key(key), BuilderFactory.BINARY);
  }

  public final CommandObject<byte[]> dump(byte[] key) {
    return new CommandObject<>(commandArguments(DUMP).key(key), BuilderFactory.BINARY);
  }

  public final CommandObject<String> restore(String key, long ttl, byte[] serializedValue) {
    return new CommandObject<>(commandArguments(RESTORE).key(key).add(ttl)
        .add(serializedValue), BuilderFactory.STRING);
  }

  public final CommandObject<String> restore(String key, long ttl, byte[] serializedValue, RestoreParams params) {
    return new CommandObject<>(commandArguments(RESTORE).key(key).add(ttl)
        .add(serializedValue).addParams(params), BuilderFactory.STRING);
  }

  public final CommandObject<String> restore(byte[] key, long ttl, byte[] serializedValue) {
    return new CommandObject<>(commandArguments(RESTORE).key(key).add(ttl)
        .add(serializedValue), BuilderFactory.STRING);
  }

  public final CommandObject<String> restore(byte[] key, long ttl, byte[] serializedValue, RestoreParams params) {
    return new CommandObject<>(commandArguments(RESTORE).key(key).add(ttl)
        .add(serializedValue).addParams(params), BuilderFactory.STRING);
  }

  public final CommandObject<Long> expire(String key, long seconds) {
    return new CommandObject<>(commandArguments(EXPIRE).key(key).add(seconds), BuilderFactory.LONG);
  }

  public final CommandObject<Long> expire(byte[] key, long seconds) {
    return new CommandObject<>(commandArguments(EXPIRE).key(key).add(seconds), BuilderFactory.LONG);
  }

  public final CommandObject<Long> pexpire(String key, long milliseconds) {
    return new CommandObject<>(commandArguments(PEXPIRE).key(key).add(milliseconds), BuilderFactory.LONG);
  }

  public final CommandObject<Long> pexpire(byte[] key, long milliseconds) {
    return new CommandObject<>(commandArguments(PEXPIRE).key(key).add(milliseconds), BuilderFactory.LONG);
  }

  public final CommandObject<Long> expireAt(String key, long unixTime) {
    return new CommandObject<>(commandArguments(EXPIREAT).key(key).add(unixTime), BuilderFactory.LONG);
  }

  public final CommandObject<Long> expireAt(byte[] key, long unixTime) {
    return new CommandObject<>(commandArguments(EXPIREAT).key(key).add(unixTime), BuilderFactory.LONG);
  }

  public final CommandObject<Long> pexpireAt(String key, long millisecondsTimestamp) {
    return new CommandObject<>(commandArguments(PEXPIREAT).key(key).add(millisecondsTimestamp), BuilderFactory.LONG);
  }

  public final CommandObject<Long> pexpireAt(byte[] key, long millisecondsTimestamp) {
    return new CommandObject<>(commandArguments(PEXPIREAT).key(key).add(millisecondsTimestamp), BuilderFactory.LONG);
  }

  public final CommandObject<Long> ttl(String key) {
    return new CommandObject<>(commandArguments(TTL).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> ttl(byte[] key) {
    return new CommandObject<>(commandArguments(TTL).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> pttl(String key) {
    return new CommandObject<>(commandArguments(PTTL).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> pttl(byte[] key) {
    return new CommandObject<>(commandArguments(PTTL).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> touch(String key) {
    return new CommandObject<>(commandArguments(TOUCH).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> touch(String... keys) {
    return new CommandObject<>(commandArguments(TOUCH).keys((Object[]) keys), BuilderFactory.LONG);
  }

  public final CommandObject<Long> touch(byte[] key) {
    return new CommandObject<>(commandArguments(TOUCH).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> touch(byte[]... keys) {
    return new CommandObject<>(commandArguments(TOUCH).keys((Object[]) keys), BuilderFactory.LONG);
  }

  public final CommandObject<List<String>> sort(String key) {
    return new CommandObject<>(commandArguments(SORT).key(key), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<List<String>> sort(String key, SortingParams sortingParameters) {
    return new CommandObject<>(commandArguments(SORT).key(key).addParams(sortingParameters), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<List<byte[]>> sort(byte[] key) {
    return new CommandObject<>(commandArguments(SORT).key(key), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<byte[]>> sort(byte[] key, SortingParams sortingParameters) {
    return new CommandObject<>(commandArguments(SORT).key(key).addParams(sortingParameters), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<Long> sort(String key, String dstkey) {
    return new CommandObject<>(commandArguments(SORT).key(key)
        .add(STORE).key(dstkey), BuilderFactory.LONG);
  }

  public final CommandObject<Long> sort(String key, SortingParams sortingParameters, String dstkey) {
    return new CommandObject<>(commandArguments(SORT).key(key).addParams(sortingParameters)
        .add(STORE).key(dstkey), BuilderFactory.LONG);
  }

  public final CommandObject<Long> sort(byte[] key, byte[] dstkey) {
    return new CommandObject<>(commandArguments(SORT).key(key)
        .add(STORE).key(dstkey), BuilderFactory.LONG);
  }

  public final CommandObject<Long> sort(byte[] key, SortingParams sortingParameters, byte[] dstkey) {
    return new CommandObject<>(commandArguments(SORT).key(key).addParams(sortingParameters)
        .add(STORE).key(dstkey), BuilderFactory.LONG);
  }

  public final CommandObject<Long> del(String key) {
    return new CommandObject<>(commandArguments(DEL).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> del(String... keys) {
    return new CommandObject<>(commandArguments(DEL).keys((Object[]) keys), BuilderFactory.LONG);
  }

  public final CommandObject<Long> del(byte[] key) {
    return new CommandObject<>(commandArguments(DEL).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> del(byte[]... keys) {
    return new CommandObject<>(commandArguments(DEL).keys((Object[]) keys), BuilderFactory.LONG);
  }

  public final CommandObject<Long> unlink(String key) {
    return new CommandObject<>(commandArguments(UNLINK).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> unlink(String... keys) {
    return new CommandObject<>(commandArguments(UNLINK).keys((Object[]) keys), BuilderFactory.LONG);
  }

  public final CommandObject<Long> unlink(byte[] key) {
    return new CommandObject<>(commandArguments(UNLINK).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> unlink(byte[]... keys) {
    return new CommandObject<>(commandArguments(UNLINK).keys((Object[]) keys), BuilderFactory.LONG);
  }

  public final CommandObject<Boolean> copy(String srcKey, String dstKey, boolean replace) {
    CommandArguments args = commandArguments(Command.COPY).key(srcKey).key(dstKey);
    if (replace) {
      args.add(REPLACE);
    }
    return new CommandObject<>(args, BuilderFactory.BOOLEAN);
  }

  public final CommandObject<Boolean> copy(byte[] srcKey, byte[] dstKey, boolean replace) {
    CommandArguments args = commandArguments(Command.COPY).key(srcKey).key(dstKey);
    if (replace) {
      args.add(REPLACE);
    }
    return new CommandObject<>(args, BuilderFactory.BOOLEAN);
  }

  public final CommandObject<String> rename(String oldkey, String newkey) {
    return new CommandObject<>(commandArguments(RENAME).key(oldkey).key(newkey), BuilderFactory.STRING);
  }

  public final CommandObject<Long> renamenx(String oldkey, String newkey) {
    return new CommandObject<>(commandArguments(RENAMENX).key(oldkey).key(newkey), BuilderFactory.LONG);
  }

  public final CommandObject<String> rename(byte[] oldkey, byte[] newkey) {
    return new CommandObject<>(commandArguments(RENAME).key(oldkey).key(newkey), BuilderFactory.STRING);
  }

  public final CommandObject<Long> renamenx(byte[] oldkey, byte[] newkey) {
    return new CommandObject<>(commandArguments(RENAMENX).key(oldkey).key(newkey), BuilderFactory.LONG);
  }

  public CommandObject<Long> dbSize() {
    return new CommandObject<>(commandArguments(DBSIZE), BuilderFactory.LONG);
  }

  public CommandObject<Set<String>> keys(String pattern) {
    CommandArguments args = commandArguments(Command.KEYS).key(pattern);
    return new CommandObject<>(args, BuilderFactory.STRING_SET);
  }

  public CommandObject<Set<byte[]>> keys(byte[] pattern) {
    CommandArguments args = commandArguments(Command.KEYS).key(pattern);
    return new CommandObject<>(args, BuilderFactory.BINARY_SET);
  }

  public CommandObject<ScanResult<String>> scan(String cursor) {
    return new CommandObject<>(commandArguments(SCAN).add(cursor), BuilderFactory.SCAN_RESPONSE);
  }

  public CommandObject<ScanResult<String>> scan(String cursor, ScanParams params) {
    return new CommandObject<>(commandArguments(SCAN).add(cursor).addParams(params), BuilderFactory.SCAN_RESPONSE);
  }

  public CommandObject<ScanResult<String>> scan(String cursor, ScanParams params, String type) {
    return new CommandObject<>(commandArguments(SCAN).add(cursor).addParams(params).add(Keyword.TYPE).add(type), BuilderFactory.SCAN_RESPONSE);
  }

  public CommandObject<ScanResult<byte[]>> scan(byte[] cursor) {
    return new CommandObject<>(commandArguments(SCAN).add(cursor), BuilderFactory.SCAN_BINARY_RESPONSE);
  }

  public CommandObject<ScanResult<byte[]>> scan(byte[] cursor, ScanParams params) {
    return new CommandObject<>(commandArguments(SCAN).add(cursor).addParams(params), BuilderFactory.SCAN_BINARY_RESPONSE);
  }

  public CommandObject<ScanResult<byte[]>> scan(byte[] cursor, ScanParams params, byte[] type) {
    return new CommandObject<>(commandArguments(SCAN).add(cursor).addParams(params).add(Keyword.TYPE).add(type), BuilderFactory.SCAN_BINARY_RESPONSE);
  }

  public final CommandObject<String> randomKey() {
    return new CommandObject<>(commandArguments(RANDOMKEY), BuilderFactory.STRING);
  }

  public final CommandObject<byte[]> randomBinaryKey() {
    return new CommandObject<>(commandArguments(RANDOMKEY), BuilderFactory.BINARY);
  }
  // Key commands

  // String commands
  public final CommandObject<String> set(String key, String value) {
    return new CommandObject<>(commandArguments(Command.SET).key(key).add(value), BuilderFactory.STRING);
  }

  public final CommandObject<String> set(String key, String value, SetParams params) {
    return new CommandObject<>(commandArguments(Command.SET).key(key).add(value).addParams(params), BuilderFactory.STRING);
  }

  public final CommandObject<String> set(byte[] key, byte[] value) {
    return new CommandObject<>(commandArguments(Command.SET).key(key).add(value), BuilderFactory.STRING);
  }

  public final CommandObject<String> set(byte[] key, byte[] value, SetParams params) {
    return new CommandObject<>(commandArguments(Command.SET).key(key).add(value).addParams(params), BuilderFactory.STRING);
  }

  public final CommandObject<String> get(String key) {
    return new CommandObject<>(commandArguments(Command.GET).key(key), BuilderFactory.STRING);
  }

  public final CommandObject<String> getDel(String key) {
    return new CommandObject<>(commandArguments(Command.GETDEL).key(key), BuilderFactory.STRING);
  }

  public final CommandObject<String> getEx(String key, GetExParams params) {
    return new CommandObject<>(commandArguments(Command.GETEX).key(key).addParams(params), BuilderFactory.STRING);
  }

  public final CommandObject<byte[]> get(byte[] key) {
    return new CommandObject<>(commandArguments(Command.GET).key(key), BuilderFactory.BINARY);
  }

  public final CommandObject<byte[]> getDel(byte[] key) {
    return new CommandObject<>(commandArguments(Command.GETDEL).key(key), BuilderFactory.BINARY);
  }

  public final CommandObject<byte[]> getEx(byte[] key, GetExParams params) {
    return new CommandObject<>(commandArguments(Command.GETEX).key(key).addParams(params), BuilderFactory.BINARY);
  }

  public final CommandObject<String> getSet(String key, String value) {
    return new CommandObject<>(commandArguments(Command.GETSET).key(key).add(value), BuilderFactory.STRING);
  }

  public final CommandObject<byte[]> getSet(byte[] key, byte[] value) {
    return new CommandObject<>(commandArguments(Command.GETSET).key(key).add(value), BuilderFactory.BINARY);
  }

  public final CommandObject<Long> setnx(String key, String value) {
    return new CommandObject<>(commandArguments(SETNX).key(key).add(value), BuilderFactory.LONG);
  }

  public final CommandObject<String> setex(String key, long seconds, String value) {
    return new CommandObject<>(commandArguments(SETEX).key(key).add(seconds).add(value), BuilderFactory.STRING);
  }

  public final CommandObject<String> psetex(String key, long milliseconds, String value) {
    return new CommandObject<>(commandArguments(PSETEX).key(key).add(milliseconds).add(value), BuilderFactory.STRING);
  }

  public final CommandObject<Long> setnx(byte[] key, byte[] value) {
    return new CommandObject<>(commandArguments(SETNX).key(key).add(value), BuilderFactory.LONG);
  }

  public final CommandObject<String> setex(byte[] key, long seconds, byte[] value) {
    return new CommandObject<>(commandArguments(SETEX).key(key).add(seconds).add(value), BuilderFactory.STRING);
  }

  public final CommandObject<String> psetex(byte[] key, long milliseconds, byte[] value) {
    return new CommandObject<>(commandArguments(PSETEX).key(key).add(milliseconds).add(value), BuilderFactory.STRING);
  }

  public final CommandObject<Boolean> setbit(String key, long offset, boolean value) {
    return new CommandObject<>(commandArguments(SETBIT).key(key).add(offset).add(value), BuilderFactory.BOOLEAN);
  }

  public final CommandObject<Boolean> setbit(byte[] key, long offset, boolean value) {
    return new CommandObject<>(commandArguments(SETBIT).key(key).add(offset).add(value), BuilderFactory.BOOLEAN);
  }

  public final CommandObject<Boolean> getbit(String key, long offset) {
    return new CommandObject<>(commandArguments(GETBIT).key(key).add(offset), BuilderFactory.BOOLEAN);
  }

  public final CommandObject<Boolean> getbit(byte[] key, long offset) {
    return new CommandObject<>(commandArguments(GETBIT).key(key).add(offset), BuilderFactory.BOOLEAN);
  }

  public final CommandObject<Long> setrange(String key, long offset, String value) {
    return new CommandObject<>(commandArguments(SETRANGE).key(key).add(offset).add(value), BuilderFactory.LONG);
  }

  public final CommandObject<Long> setrange(byte[] key, long offset, byte[] value) {
    return new CommandObject<>(commandArguments(SETRANGE).key(key).add(offset).add(value), BuilderFactory.LONG);
  }

  public final CommandObject<String> getrange(String key, long startOffset, long endOffset) {
    return new CommandObject<>(commandArguments(GETRANGE).key(key).add(startOffset).add(endOffset), BuilderFactory.STRING);
  }

  public final CommandObject<byte[]> getrange(byte[] key, long startOffset, long endOffset) {
    return new CommandObject<>(commandArguments(GETRANGE).key(key).add(startOffset).add(endOffset), BuilderFactory.BINARY);
  }

  public final CommandObject<List<String>> mget(String... keys) {
    return new CommandObject<>(commandArguments(MGET).keys((Object[]) keys), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<List<byte[]>> mget(byte[]... keys) {
    return new CommandObject<>(commandArguments(MGET).keys((Object[]) keys), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<String> mset(String... keysvalues) {
    return new CommandObject<>(addFlatKeyValueArgs(commandArguments(MSET), keysvalues), BuilderFactory.STRING);
  }

  public final CommandObject<Long> msetnx(String... keysvalues) {
    return new CommandObject<>(addFlatKeyValueArgs(commandArguments(MSETNX), keysvalues), BuilderFactory.LONG);
  }

  public final CommandObject<String> mset(byte[]... keysvalues) {
    return new CommandObject<>(addFlatKeyValueArgs(commandArguments(MSET), keysvalues), BuilderFactory.STRING);
  }

  public final CommandObject<Long> msetnx(byte[]... keysvalues) {
    return new CommandObject<>(addFlatKeyValueArgs(commandArguments(MSETNX), keysvalues), BuilderFactory.LONG);
  }

  public final CommandObject<Long> incr(String key) {
    return new CommandObject<>(commandArguments(Command.INCR).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> incrBy(String key, long increment) {
    return new CommandObject<>(commandArguments(INCRBY).key(key).add(increment), BuilderFactory.LONG);
  }

  public final CommandObject<Double> incrByFloat(String key, double increment) {
    return new CommandObject<>(commandArguments(INCRBYFLOAT).key(key).add(increment), BuilderFactory.DOUBLE);
  }

  public final CommandObject<Long> incr(byte[] key) {
    return new CommandObject<>(commandArguments(Command.INCR).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> incrBy(byte[] key, long increment) {
    return new CommandObject<>(commandArguments(INCRBY).key(key).add(increment), BuilderFactory.LONG);
  }

  public final CommandObject<Double> incrByFloat(byte[] key, double increment) {
    return new CommandObject<>(commandArguments(INCRBYFLOAT).key(key).add(increment), BuilderFactory.DOUBLE);
  }

  public final CommandObject<Long> decr(String key) {
    return new CommandObject<>(commandArguments(DECR).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> decrBy(String key, long decrement) {
    return new CommandObject<>(commandArguments(DECRBY).key(key).add(decrement), BuilderFactory.LONG);
  }

  public final CommandObject<Long> decr(byte[] key) {
    return new CommandObject<>(commandArguments(DECR).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> decrBy(byte[] key, long decrement) {
    return new CommandObject<>(commandArguments(DECRBY).key(key).add(decrement), BuilderFactory.LONG);
  }

  public final CommandObject<Long> append(String key, String value) {
    return new CommandObject<>(commandArguments(APPEND).key(key).add(value), BuilderFactory.LONG);
  }

  public final CommandObject<Long> append(byte[] key, byte[] value) {
    return new CommandObject<>(commandArguments(APPEND).key(key).add(value), BuilderFactory.LONG);
  }

  public final CommandObject<String> substr(String key, int start, int end) {
    return new CommandObject<>(commandArguments(SUBSTR).key(key).add(start).add(end), BuilderFactory.STRING);
  }

  public final CommandObject<byte[]> substr(byte[] key, int start, int end) {
    return new CommandObject<>(commandArguments(SUBSTR).key(key).add(start).add(end), BuilderFactory.BINARY);
  }

  public final CommandObject<Long> strlen(String key) {
    return new CommandObject<>(commandArguments(STRLEN).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> strlen(byte[] key) {
    return new CommandObject<>(commandArguments(STRLEN).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> bitcount(String key) {
    return new CommandObject<>(commandArguments(BITCOUNT).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> bitcount(String key, long start, long end) {
    return new CommandObject<>(commandArguments(BITCOUNT).key(key).add(start).add(end), BuilderFactory.LONG);
  }

  public final CommandObject<Long> bitcount(byte[] key) {
    return new CommandObject<>(commandArguments(BITCOUNT).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> bitcount(byte[] key, long start, long end) {
    return new CommandObject<>(commandArguments(BITCOUNT).key(key).add(start).add(end), BuilderFactory.LONG);
  }

  public final CommandObject<Long> bitpos(String key, boolean value) {
    return new CommandObject<>(commandArguments(BITPOS).key(key).add(value ? 1 : 0), BuilderFactory.LONG);
  }

  public final CommandObject<Long> bitpos(String key, boolean value, BitPosParams params) {
    return new CommandObject<>(commandArguments(BITPOS).key(key).add(value ? 1 : 0).addParams(params), BuilderFactory.LONG);
  }

  public final CommandObject<Long> bitpos(byte[] key, boolean value) {
    return new CommandObject<>(commandArguments(BITPOS).key(key).add(value ? 1 : 0), BuilderFactory.LONG);
  }

  public final CommandObject<Long> bitpos(byte[] key, boolean value, BitPosParams params) {
    return new CommandObject<>(commandArguments(BITPOS).key(key).add(value ? 1 : 0).addParams(params), BuilderFactory.LONG);
  }

  public final CommandObject<List<Long>> bitfield(String key, String... arguments) {
    return new CommandObject<>(commandArguments(BITFIELD).key(key).addObjects((Object[]) arguments), BuilderFactory.LONG_LIST);
  }

  public final CommandObject<List<Long>> bitfieldReadonly(String key, String... arguments) {
    return new CommandObject<>(commandArguments(BITFIELD_RO).key(key).addObjects((Object[]) arguments), BuilderFactory.LONG_LIST);
  }

  public final CommandObject<List<Long>> bitfield(byte[] key, byte[]... arguments) {
    return new CommandObject<>(commandArguments(BITFIELD).key(key).addObjects((Object[]) arguments), BuilderFactory.LONG_LIST);
  }

  public final CommandObject<List<Long>> bitfieldReadonly(byte[] key, byte[]... arguments) {
    return new CommandObject<>(commandArguments(BITFIELD_RO).key(key).addObjects((Object[]) arguments), BuilderFactory.LONG_LIST);
  }

  public final CommandObject<Long> bitop(BitOP op, String destKey, String... srcKeys) {
    return new CommandObject<>(commandArguments(BITOP).add(op).key(destKey).keys((Object[]) srcKeys), BuilderFactory.LONG);
  }

  public final CommandObject<Long> bitop(BitOP op, byte[] destKey, byte[]... srcKeys) {
    return new CommandObject<>(commandArguments(BITOP).add(op).key(destKey).keys((Object[]) srcKeys), BuilderFactory.LONG);
  }

  public final CommandObject<LCSMatchResult> strAlgoLCSKeys(String keyA, String keyB, StrAlgoLCSParams params) {
    return new CommandObject<>(commandArguments(STRALGO).add(LCS).add(Keyword.KEYS)
        .key(keyA).key(keyB).addParams(params),
        BuilderFactory.STR_ALGO_LCS_RESULT_BUILDER);
  }

  public final CommandObject<LCSMatchResult> strAlgoLCSKeys(byte[] keyA, byte[] keyB, StrAlgoLCSParams params) {
    return new CommandObject<>(commandArguments(STRALGO).add(LCS).add(STRINGS)
        .key(keyA).key(keyB).addParams(params),
        BuilderFactory.STR_ALGO_LCS_RESULT_BUILDER);
  }
  // String commands

  // List commands
  public final CommandObject<Long> rpush(String key, String... strings) {
    return new CommandObject<>(commandArguments(RPUSH).key(key).addObjects((Object[]) strings), BuilderFactory.LONG);
  }

  public final CommandObject<Long> rpush(byte[] key, byte[]... strings) {
    return new CommandObject<>(commandArguments(RPUSH).key(key).addObjects((Object[]) strings), BuilderFactory.LONG);
  }

  public final CommandObject<Long> lpush(String key, String... strings) {
    return new CommandObject<>(commandArguments(LPUSH).key(key).addObjects((Object[]) strings), BuilderFactory.LONG);
  }

  public final CommandObject<Long> lpush(byte[] key, byte[]... strings) {
    return new CommandObject<>(commandArguments(LPUSH).key(key).addObjects((Object[]) strings), BuilderFactory.LONG);
  }

  public final CommandObject<Long> llen(String key) {
    return new CommandObject<>(commandArguments(LLEN).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> llen(byte[] key) {
    return new CommandObject<>(commandArguments(LLEN).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<List<String>> lrange(String key, long start, long stop) {
    return new CommandObject<>(commandArguments(LRANGE).key(key).add(start).add(stop), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<List<byte[]>> lrange(byte[] key, long start, long stop) {
    return new CommandObject<>(commandArguments(LRANGE).key(key).add(start).add(stop), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<String> ltrim(String key, long start, long stop) {
    return new CommandObject<>(commandArguments(LTRIM).key(key).add(start).add(stop), BuilderFactory.STRING);
  }

  public final CommandObject<String> ltrim(byte[] key, long start, long stop) {
    return new CommandObject<>(commandArguments(LTRIM).key(key).add(start).add(stop), BuilderFactory.STRING);
  }

  public final CommandObject<String> lindex(String key, long index) {
    return new CommandObject<>(commandArguments(LINDEX).key(key).add(index), BuilderFactory.STRING);
  }

  public final CommandObject<byte[]> lindex(byte[] key, long index) {
    return new CommandObject<>(commandArguments(LINDEX).key(key).add(index), BuilderFactory.BINARY);
  }

  public final CommandObject<String> lset(String key, long index, String value) {
    return new CommandObject<>(commandArguments(LSET).key(key).add(index).add(value), BuilderFactory.STRING);
  }

  public final CommandObject<String> lset(byte[] key, long index, byte[] value) {
    return new CommandObject<>(commandArguments(LSET).key(key).add(index).add(value), BuilderFactory.STRING);
  }

  public final CommandObject<Long> lrem(String key, long count, String value) {
    return new CommandObject<>(commandArguments(LREM).key(key).add(count).add(value), BuilderFactory.LONG);
  }

  public final CommandObject<Long> lrem(byte[] key, long count, byte[] value) {
    return new CommandObject<>(commandArguments(LREM).key(key).add(count).add(value), BuilderFactory.LONG);
  }

  public final CommandObject<String> lpop(String key) {
    return new CommandObject<>(commandArguments(LPOP).key(key), BuilderFactory.STRING);
  }

  public final CommandObject<List<String>> lpop(String key, int count) {
    return new CommandObject<>(commandArguments(LPOP).key(key).add(count), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<byte[]> lpop(byte[] key) {
    return new CommandObject<>(commandArguments(LPOP).key(key), BuilderFactory.BINARY);
  }

  public final CommandObject<List<byte[]>> lpop(byte[] key, int count) {
    return new CommandObject<>(commandArguments(LPOP).key(key).add(count), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<String> rpop(String key) {
    return new CommandObject<>(commandArguments(RPOP).key(key), BuilderFactory.STRING);
  }

  public final CommandObject<List<String>> rpop(String key, int count) {
    return new CommandObject<>(commandArguments(RPOP).key(key).add(count), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<byte[]> rpop(byte[] key) {
    return new CommandObject<>(commandArguments(RPOP).key(key), BuilderFactory.BINARY);
  }

  public final CommandObject<List<byte[]>> rpop(byte[] key, int count) {
    return new CommandObject<>(commandArguments(RPOP).key(key).add(count), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<Long> lpos(String key, String element) {
    return new CommandObject<>(commandArguments(LPOS).key(key).add(element), BuilderFactory.LONG);
  }

  public final CommandObject<Long> lpos(String key, String element, LPosParams params) {
    return new CommandObject<>(commandArguments(LPOS).key(key).add(element).addParams(params), BuilderFactory.LONG);
  }

  public final CommandObject<List<Long>> lpos(String key, String element, LPosParams params, long count) {
    return new CommandObject<>(commandArguments(LPOS).key(key).add(element)
        .addParams(params).add(COUNT).add(count), BuilderFactory.LONG_LIST);
  }

  public final CommandObject<Long> lpos(byte[] key, byte[] element) {
    return new CommandObject<>(commandArguments(LPOS).key(key).add(element), BuilderFactory.LONG);
  }

  public final CommandObject<Long> lpos(byte[] key, byte[] element, LPosParams params) {
    return new CommandObject<>(commandArguments(LPOS).key(key).add(element).addParams(params), BuilderFactory.LONG);
  }

  public final CommandObject<List<Long>> lpos(byte[] key, byte[] element, LPosParams params, long count) {
    return new CommandObject<>(commandArguments(LPOS).key(key).add(element)
        .addParams(params).add(COUNT).add(count), BuilderFactory.LONG_LIST);
  }

  public final CommandObject<Long> linsert(String key, ListPosition where, String pivot, String value) {
    return new CommandObject<>(commandArguments(LINSERT).key(key).add(where)
        .add(pivot).add(value), BuilderFactory.LONG);
  }

  public final CommandObject<Long> linsert(byte[] key, ListPosition where, byte[] pivot, byte[] value) {
    return new CommandObject<>(commandArguments(LINSERT).key(key).add(where)
        .add(pivot).add(value), BuilderFactory.LONG);
  }

  public final CommandObject<Long> lpushx(String key, String... string) {
    return new CommandObject<>(commandArguments(LPUSHX).key(key).addObjects((Object[]) string), BuilderFactory.LONG);
  }

  public final CommandObject<Long> rpushx(String key, String... string) {
    return new CommandObject<>(commandArguments(RPUSHX).key(key).addObjects((Object[]) string), BuilderFactory.LONG);
  }

  public final CommandObject<Long> lpushx(byte[] key, byte[]... arg) {
    return new CommandObject<>(commandArguments(LPUSHX).key(key).addObjects((Object[]) arg), BuilderFactory.LONG);
  }

  public final CommandObject<Long> rpushx(byte[] key, byte[]... arg) {
    return new CommandObject<>(commandArguments(RPUSHX).key(key).addObjects((Object[]) arg), BuilderFactory.LONG);
  }

  public final CommandObject<List<String>> blpop(int timeout, String key) {
    return new CommandObject<>(commandArguments(BLPOP).blocking().key(key).add(timeout), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<List<String>> blpop(int timeout, String... keys) {
    return new CommandObject<>(commandArguments(BLPOP).blocking().keys((Object[]) keys).add(timeout), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<KeyedListElement> blpop(double timeout, String key) {
    return new CommandObject<>(commandArguments(BLPOP).blocking().key(key).add(timeout), BuilderFactory.KEYED_LIST_ELEMENT);
  }

  public final CommandObject<KeyedListElement> blpop(double timeout, String... keys) {
    return new CommandObject<>(commandArguments(BLPOP).blocking().keys((Object[]) keys).add(timeout), BuilderFactory.KEYED_LIST_ELEMENT);
  }

  public final CommandObject<List<byte[]>> blpop(int timeout, byte[]... keys) {
    return new CommandObject<>(commandArguments(BLPOP).blocking().keys((Object[]) keys).add(timeout), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<byte[]>> blpop(double timeout, byte[]... keys) {
    return new CommandObject<>(commandArguments(BLPOP).blocking().keys((Object[]) keys).add(timeout), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<String>> brpop(int timeout, String key) {
    return new CommandObject<>(commandArguments(BRPOP).blocking().key(key).add(timeout), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<List<String>> brpop(int timeout, String... keys) {
    return new CommandObject<>(commandArguments(BRPOP).blocking().keys((Object[]) keys).add(timeout), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<KeyedListElement> brpop(double timeout, String key) {
    return new CommandObject<>(commandArguments(BRPOP).blocking().key(key).add(timeout), BuilderFactory.KEYED_LIST_ELEMENT);
  }

  public final CommandObject<KeyedListElement> brpop(double timeout, String... keys) {
    return new CommandObject<>(commandArguments(BRPOP).blocking().keys((Object[]) keys).add(timeout), BuilderFactory.KEYED_LIST_ELEMENT);
  }

  public final CommandObject<List<byte[]>> brpop(int timeout, byte[]... keys) {
    return new CommandObject<>(commandArguments(BRPOP).blocking().keys((Object[]) keys).add(timeout), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<byte[]>> brpop(double timeout, byte[]... keys) {
    return new CommandObject<>(commandArguments(BRPOP).blocking().keys((Object[]) keys).add(timeout), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<String> rpoplpush(String srckey, String dstkey) {
    return new CommandObject<>(commandArguments(RPOPLPUSH).key(srckey).key(dstkey), BuilderFactory.STRING);
  }

  public final CommandObject<String> brpoplpush(String source, String destination, int timeout) {
    return new CommandObject<>(commandArguments(BRPOPLPUSH).blocking().key(source)
        .key(destination).add(timeout), BuilderFactory.STRING);
  }

  public final CommandObject<byte[]> rpoplpush(byte[] srckey, byte[] dstkey) {
    return new CommandObject<>(commandArguments(RPOPLPUSH).key(srckey).key(dstkey), BuilderFactory.BINARY);
  }

  public final CommandObject<byte[]> brpoplpush(byte[] source, byte[] destination, int timeout) {
    return new CommandObject<>(commandArguments(BRPOPLPUSH).blocking().key(source)
        .key(destination).add(timeout), BuilderFactory.BINARY);
  }

  public final CommandObject<String> lmove(String srcKey, String dstKey, ListDirection from, ListDirection to) {
    return new CommandObject<>(commandArguments(LMOVE).key(srcKey).key(dstKey)
        .add(from).add(to), BuilderFactory.STRING);
  }

  public final CommandObject<String> blmove(String srcKey, String dstKey, ListDirection from, ListDirection to, double timeout) {
    return new CommandObject<>(commandArguments(BLMOVE).blocking().key(srcKey)
        .key(dstKey).add(from).add(to).add(timeout), BuilderFactory.STRING);
  }

  public final CommandObject<byte[]> lmove(byte[] srcKey, byte[] dstKey, ListDirection from, ListDirection to) {
    return new CommandObject<>(commandArguments(LMOVE).key(srcKey).key(dstKey)
        .add(from).add(to), BuilderFactory.BINARY);
  }

  public final CommandObject<byte[]> blmove(byte[] srcKey, byte[] dstKey, ListDirection from, ListDirection to, double timeout) {
    return new CommandObject<>(commandArguments(BLMOVE).blocking().key(srcKey)
        .key(dstKey).add(from).add(to).add(timeout), BuilderFactory.BINARY);
  }
  // List commands

  // Hash commands
  public final CommandObject<Long> hset(String key, String field, String value) {
    return new CommandObject<>(commandArguments(HSET).key(key).add(field).add(value), BuilderFactory.LONG);
  }

  public final CommandObject<Long> hset(String key, Map<String, String> hash) {
    return new CommandObject<>(addFlatMapArgs(commandArguments(HSET).key(key), hash), BuilderFactory.LONG);
  }

  public final CommandObject<String> hget(String key, String field) {
    return new CommandObject<>(commandArguments(HGET).key(key).add(field), BuilderFactory.STRING);
  }

  public final CommandObject<Long> hsetnx(String key, String field, String value) {
    return new CommandObject<>(commandArguments(HSETNX).key(key).add(field).add(value), BuilderFactory.LONG);
  }

  public final CommandObject<String> hmset(String key, Map<String, String> hash) {
    return new CommandObject<>(addFlatMapArgs(commandArguments(HMSET).key(key), hash), BuilderFactory.STRING);
  }

  public final CommandObject<List<String>> hmget(String key, String... fields) {
    return new CommandObject<>(commandArguments(HMGET).key(key).addObjects((Object[]) fields), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<Long> hset(byte[] key, byte[] field, byte[] value) {
    return new CommandObject<>(commandArguments(HSET).key(key).add(field).add(value), BuilderFactory.LONG);
  }

  public final CommandObject<Long> hset(byte[] key, Map<byte[], byte[]> hash) {
    return new CommandObject<>(addFlatMapArgs(commandArguments(HSET).key(key), hash), BuilderFactory.LONG);
  }

  public final CommandObject<byte[]> hget(byte[] key, byte[] field) {
    return new CommandObject<>(commandArguments(HGET).key(key).add(field), BuilderFactory.BINARY);
  }

  public final CommandObject<Long> hsetnx(byte[] key, byte[] field, byte[] value) {
    return new CommandObject<>(commandArguments(HSETNX).key(key).add(field).add(value), BuilderFactory.LONG);
  }

  public final CommandObject<String> hmset(byte[] key, Map<byte[], byte[]> hash) {
    return new CommandObject<>(addFlatMapArgs(commandArguments(HMSET).key(key), hash), BuilderFactory.STRING);
  }

  public final CommandObject<List<byte[]>> hmget(byte[] key, byte[]... fields) {
    return new CommandObject<>(commandArguments(HMGET).key(key).addObjects((Object[]) fields), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<Long> hincrBy(String key, String field, long value) {
    return new CommandObject<>(commandArguments(HINCRBY).key(key).add(field).add(value), BuilderFactory.LONG);
  }

  public final CommandObject<Double> hincrByFloat(String key, String field, double value) {
    return new CommandObject<>(commandArguments(HINCRBYFLOAT).key(key).add(field).add(value), BuilderFactory.DOUBLE);
  }

  public final CommandObject<Boolean> hexists(String key, String field) {
    return new CommandObject<>(commandArguments(HEXISTS).key(key).add(field), BuilderFactory.BOOLEAN);
  }

  public final CommandObject<Long> hdel(String key, String... field) {
    return new CommandObject<>(commandArguments(HDEL).key(key).addObjects((Object[]) field), BuilderFactory.LONG);
  }

  public final CommandObject<Long> hlen(String key) {
    return new CommandObject<>(commandArguments(HLEN).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> hincrBy(byte[] key, byte[] field, long value) {
    return new CommandObject<>(commandArguments(HINCRBY).key(key).add(field).add(value), BuilderFactory.LONG);
  }

  public final CommandObject<Double> hincrByFloat(byte[] key, byte[] field, double value) {
    return new CommandObject<>(commandArguments(HINCRBYFLOAT).key(key).add(field).add(value), BuilderFactory.DOUBLE);
  }

  public final CommandObject<Boolean> hexists(byte[] key, byte[] field) {
    return new CommandObject<>(commandArguments(HEXISTS).key(key).add(field), BuilderFactory.BOOLEAN);
  }

  public final CommandObject<Long> hdel(byte[] key, byte[]... field) {
    return new CommandObject<>(commandArguments(HDEL).key(key).addObjects((Object[]) field), BuilderFactory.LONG);
  }

  public final CommandObject<Long> hlen(byte[] key) {
    return new CommandObject<>(commandArguments(HLEN).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Set<String>> hkeys(String key) {
    return new CommandObject<>(commandArguments(HKEYS).key(key), BuilderFactory.STRING_SET);
  }

  public final CommandObject<List<String>> hvals(String key) {
    return new CommandObject<>(commandArguments(HVALS).key(key), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<Set<byte[]>> hkeys(byte[] key) {
    return new CommandObject<>(commandArguments(HKEYS).key(key), BuilderFactory.BINARY_SET);
  }

  public final CommandObject<List<byte[]>> hvals(byte[] key) {
    return new CommandObject<>(commandArguments(HVALS).key(key), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<Map<String, String>> hgetAll(String key) {
    return new CommandObject<>(commandArguments(HGETALL).key(key), BuilderFactory.STRING_MAP);
  }

  public final CommandObject<String> hrandfield(String key) {
    return new CommandObject<>(commandArguments(HRANDFIELD).key(key), BuilderFactory.STRING);
  }

  public final CommandObject<List<String>> hrandfield(String key, long count) {
    return new CommandObject<>(commandArguments(HRANDFIELD).key(key).add(count), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<Map<String, String>> hrandfieldWithValues(String key, long count) {
    return new CommandObject<>(commandArguments(HRANDFIELD).key(key).add(count).add(WITHVALUES), BuilderFactory.STRING_MAP);
  }

  public final CommandObject<Map<byte[], byte[]>> hgetAll(byte[] key) {
    return new CommandObject<>(commandArguments(HGETALL).key(key), BuilderFactory.BINARY_MAP);
  }

  public final CommandObject<byte[]> hrandfield(byte[] key) {
    return new CommandObject<>(commandArguments(HRANDFIELD).key(key), BuilderFactory.BINARY);
  }

  public final CommandObject<List<byte[]>> hrandfield(byte[] key, long count) {
    return new CommandObject<>(commandArguments(HRANDFIELD).key(key).add(count), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<Map<byte[], byte[]>> hrandfieldWithValues(byte[] key, long count) {
    return new CommandObject<>(commandArguments(HRANDFIELD).key(key).add(count).add(WITHVALUES), BuilderFactory.BINARY_MAP);
  }

  public final CommandObject<ScanResult<Map.Entry<String, String>>> hscan(String key, String cursor, ScanParams params) {
    return new CommandObject<>(commandArguments(HSCAN).key(key).add(cursor).addParams(params), BuilderFactory.HSCAN_RESPONSE);
  }

  public final CommandObject<Long> hstrlen(String key, String field) {
    return new CommandObject<>(commandArguments(HSTRLEN).key(key).add(field), BuilderFactory.LONG);
  }

  public final CommandObject<ScanResult<Map.Entry<byte[], byte[]>>> hscan(byte[] key, byte[] cursor, ScanParams params) {
    return new CommandObject<>(commandArguments(HSCAN).key(key).add(cursor).addParams(params), BuilderFactory.HSCAN_BINARY_RESPONSE);
  }

  public final CommandObject<Long> hstrlen(byte[] key, byte[] field) {
    return new CommandObject<>(commandArguments(HSTRLEN).key(key).add(field), BuilderFactory.LONG);
  }
  // Hash commands

  // Set commands
  public final CommandObject<Long> sadd(String key, String... members) {
    return new CommandObject<>(commandArguments(SADD).key(key).addObjects((Object[]) members), BuilderFactory.LONG);
  }

  public final CommandObject<Long> sadd(byte[] key, byte[]... members) {
    return new CommandObject<>(commandArguments(SADD).key(key).addObjects((Object[]) members), BuilderFactory.LONG);
  }

  public final CommandObject<Set<String>> smembers(String key) {
    return new CommandObject<>(commandArguments(SMEMBERS).key(key), BuilderFactory.STRING_SET);
  }

  public final CommandObject<Set<byte[]>> smembers(byte[] key) {
    return new CommandObject<>(commandArguments(SMEMBERS).key(key), BuilderFactory.BINARY_SET);
  }

  public final CommandObject<Long> srem(String key, String... members) {
    return new CommandObject<>(commandArguments(SREM).key(key).addObjects((Object[]) members), BuilderFactory.LONG);
  }

  public final CommandObject<Long> srem(byte[] key, byte[]... members) {
    return new CommandObject<>(commandArguments(SREM).key(key).addObjects((Object[]) members), BuilderFactory.LONG);
  }

  public final CommandObject<String> spop(String key) {
    return new CommandObject<>(commandArguments(SPOP).key(key), BuilderFactory.STRING);
  }

  public final CommandObject<byte[]> spop(byte[] key) {
    return new CommandObject<>(commandArguments(SPOP).key(key), BuilderFactory.BINARY);
  }

  public final CommandObject<Set<String>> spop(String key, long count) {
    return new CommandObject<>(commandArguments(SPOP).key(key).add(count), BuilderFactory.STRING_SET);
  }

  public final CommandObject<Set<byte[]>> spop(byte[] key, long count) {
    return new CommandObject<>(commandArguments(SPOP).key(key).add(count), BuilderFactory.BINARY_SET);
  }

  public final CommandObject<Long> scard(String key) {
    return new CommandObject<>(commandArguments(SCARD).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> scard(byte[] key) {
    return new CommandObject<>(commandArguments(SCARD).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Boolean> sismember(String key, String member) {
    return new CommandObject<>(commandArguments(SISMEMBER).key(key).add(member), BuilderFactory.BOOLEAN);
  }

  public final CommandObject<Boolean> sismember(byte[] key, byte[] member) {
    return new CommandObject<>(commandArguments(SISMEMBER).key(key).add(member), BuilderFactory.BOOLEAN);
  }

  public final CommandObject<List<Boolean>> smismember(String key, String... members) {
    return new CommandObject<>(commandArguments(SMISMEMBER).key(key).addObjects((Object[]) members), BuilderFactory.BOOLEAN_LIST);
  }

  public final CommandObject<List<Boolean>> smismember(byte[] key, byte[]... members) {
    return new CommandObject<>(commandArguments(SMISMEMBER).key(key).addObjects((Object[]) members), BuilderFactory.BOOLEAN_LIST);
  }

  public final CommandObject<String> srandmember(String key) {
    return new CommandObject<>(commandArguments(SRANDMEMBER).key(key), BuilderFactory.STRING);
  }

  public final CommandObject<byte[]> srandmember(byte[] key) {
    return new CommandObject<>(commandArguments(SRANDMEMBER).key(key), BuilderFactory.BINARY);
  }

  public final CommandObject<List<String>> srandmember(String key, int count) {
    return new CommandObject<>(commandArguments(SRANDMEMBER).key(key).add(count), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<List<byte[]>> srandmember(byte[] key, int count) {
    return new CommandObject<>(commandArguments(SRANDMEMBER).key(key).add(count), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<ScanResult<String>> sscan(String key, String cursor, ScanParams params) {
    return new CommandObject<>(commandArguments(SSCAN).key(key).add(cursor).addParams(params), BuilderFactory.SSCAN_RESPONSE);
  }

  public final CommandObject<ScanResult<byte[]>> sscan(byte[] key, byte[] cursor, ScanParams params) {
    return new CommandObject<>(commandArguments(SSCAN).key(key).add(cursor).addParams(params), BuilderFactory.SSCAN_BINARY_RESPONSE);
  }

  public final CommandObject<Set<String>> sdiff(String... keys) {
    return new CommandObject<>(commandArguments(SDIFF).keys((Object[]) keys), BuilderFactory.STRING_SET);
  }

  public final CommandObject<Long> sdiffstore(String dstkey, String... keys) {
    return new CommandObject<>(commandArguments(SDIFFSTORE).key(dstkey).keys((Object[]) keys), BuilderFactory.LONG);
  }

  public final CommandObject<Set<byte[]>> sdiff(byte[]... keys) {
    return new CommandObject<>(commandArguments(SDIFF).keys((Object[]) keys), BuilderFactory.BINARY_SET);
  }

  public final CommandObject<Long> sdiffstore(byte[] dstkey, byte[]... keys) {
    return new CommandObject<>(commandArguments(SDIFFSTORE).key(dstkey).keys((Object[]) keys), BuilderFactory.LONG);
  }

  public final CommandObject<Set<String>> sinter(String... keys) {
    return new CommandObject<>(commandArguments(SINTER).keys((Object[]) keys), BuilderFactory.STRING_SET);
  }

  public final CommandObject<Long> sinterstore(String dstkey, String... keys) {
    return new CommandObject<>(commandArguments(SINTERSTORE).key(dstkey).keys((Object[]) keys), BuilderFactory.LONG);
  }

  public final CommandObject<Set<byte[]>> sinter(byte[]... keys) {
    return new CommandObject<>(commandArguments(SINTER).keys((Object[]) keys), BuilderFactory.BINARY_SET);
  }

  public final CommandObject<Long> sinterstore(byte[] dstkey, byte[]... keys) {
    return new CommandObject<>(commandArguments(SINTERSTORE).key(dstkey).keys((Object[]) keys), BuilderFactory.LONG);
  }

  public final CommandObject<Set<String>> sunion(String... keys) {
    return new CommandObject<>(commandArguments(SUNION).keys((Object[]) keys), BuilderFactory.STRING_SET);
  }

  public final CommandObject<Long> sunionstore(String dstkey, String... keys) {
    return new CommandObject<>(commandArguments(SUNIONSTORE).key(dstkey).keys((Object[]) keys), BuilderFactory.LONG);
  }

  public final CommandObject<Set<byte[]>> sunion(byte[]... keys) {
    return new CommandObject<>(commandArguments(SUNION).keys((Object[]) keys), BuilderFactory.BINARY_SET);
  }

  public final CommandObject<Long> sunionstore(byte[] dstkey, byte[]... keys) {
    return new CommandObject<>(commandArguments(SUNIONSTORE).key(dstkey).keys((Object[]) keys), BuilderFactory.LONG);
  }

  public final CommandObject<Long> smove(String srckey, String dstkey, String member) {
    return new CommandObject<>(commandArguments(SMOVE).key(srckey).key(dstkey).add(member), BuilderFactory.LONG);
  }

  public final CommandObject<Long> smove(byte[] srckey, byte[] dstkey, byte[] member) {
    return new CommandObject<>(commandArguments(SMOVE).key(srckey).key(dstkey).add(member), BuilderFactory.LONG);
  }
  // Set commands

  // Sorted Set commands
  public final CommandObject<Long> zadd(String key, double score, String member) {
    return new CommandObject<>(commandArguments(ZADD).key(key).add(score).add(member), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zadd(String key, double score, String member, ZAddParams params) {
    return new CommandObject<>(commandArguments(ZADD).key(key).addParams(params)
        .add(score).add(member), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zadd(String key, Map<String, Double> scoreMembers) {
    return new CommandObject<>(addSortedSetFlatMapArgs(commandArguments(ZADD).key(key), scoreMembers), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
    return new CommandObject<>(addSortedSetFlatMapArgs(commandArguments(ZADD).key(key).addParams(params), scoreMembers), BuilderFactory.LONG);
  }

  public final CommandObject<Double> zaddIncr(String key, double score, String member, ZAddParams params) {
    return new CommandObject<>(commandArguments(ZADD).key(key).add(Keyword.INCR)
        .addParams(params).add(score).add(member), BuilderFactory.DOUBLE);
  }

  public final CommandObject<Long> zadd(byte[] key, double score, byte[] member) {
    return new CommandObject<>(commandArguments(ZADD).key(key).add(score).add(member), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zadd(byte[] key, double score, byte[] member, ZAddParams params) {
    return new CommandObject<>(commandArguments(ZADD).key(key).addParams(params)
        .add(score).add(member), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zadd(byte[] key, Map<byte[], Double> scoreMembers) {
    return new CommandObject<>(addSortedSetFlatMapArgs(commandArguments(ZADD).key(key), scoreMembers), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zadd(byte[] key, Map<byte[], Double> scoreMembers, ZAddParams params) {
    return new CommandObject<>(addSortedSetFlatMapArgs(commandArguments(ZADD).key(key).addParams(params), scoreMembers), BuilderFactory.LONG);
  }

  public final CommandObject<Double> zaddIncr(byte[] key, double score, byte[] member, ZAddParams params) {
    return new CommandObject<>(commandArguments(ZADD).key(key).add(Keyword.INCR)
        .addParams(params).add(score).add(member), BuilderFactory.DOUBLE);
  }

  public final CommandObject<Double> zincrby(String key, double increment, String member) {
    return new CommandObject<>(commandArguments(ZINCRBY).key(key).add(increment).add(member), BuilderFactory.DOUBLE);
  }

  public final CommandObject<Double> zincrby(String key, double increment, String member, ZIncrByParams params) {
    return new CommandObject<>(commandArguments(ZADD).key(key).addParams(params).add(increment).add(member), BuilderFactory.DOUBLE);
  }

  public final CommandObject<Double> zincrby(byte[] key, double increment, byte[] member) {
    return new CommandObject<>(commandArguments(ZINCRBY).key(key).add(increment).add(member), BuilderFactory.DOUBLE);
  }

  public final CommandObject<Double> zincrby(byte[] key, double increment, byte[] member, ZIncrByParams params) {
    return new CommandObject<>(commandArguments(ZADD).key(key).addParams(params).add(increment).add(member), BuilderFactory.DOUBLE);
  }

  public final CommandObject<Long> zrem(String key, String... members) {
    return new CommandObject<>(commandArguments(ZREM).key(key).addObjects((Object[]) members), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zrem(byte[] key, byte[]... members) {
    return new CommandObject<>(commandArguments(ZREM).key(key).addObjects((Object[]) members), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zrank(String key, String member) {
    return new CommandObject<>(commandArguments(ZRANK).key(key).add(member), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zrevrank(String key, String member) {
    return new CommandObject<>(commandArguments(ZREVRANK).key(key).add(member), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zrank(byte[] key, byte[] member) {
    return new CommandObject<>(commandArguments(ZRANK).key(key).add(member), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zrevrank(byte[] key, byte[] member) {
    return new CommandObject<>(commandArguments(ZREVRANK).key(key).add(member), BuilderFactory.LONG);
  }

  public final CommandObject<String> zrandmember(String key) {
    return new CommandObject<>(commandArguments(ZRANDMEMBER).key(key), BuilderFactory.STRING);
  }

  public final CommandObject<List<String>> zrandmember(String key, long count) {
    return new CommandObject<>(commandArguments(ZRANDMEMBER).key(key).add(count), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<List<Tuple>> zrandmemberWithScores(String key, long count) {
    return new CommandObject<>(commandArguments(ZRANDMEMBER).key(key).add(count).add(WITHSCORES), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<byte[]> zrandmember(byte[] key) {
    return new CommandObject<>(commandArguments(ZRANDMEMBER).key(key), BuilderFactory.BINARY);
  }

  public final CommandObject<List<byte[]>> zrandmember(byte[] key, long count) {
    return new CommandObject<>(commandArguments(ZRANDMEMBER).key(key).add(count), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<Tuple>> zrandmemberWithScores(byte[] key, long count) {
    return new CommandObject<>(commandArguments(ZRANDMEMBER).key(key).add(count).add(WITHSCORES), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<Long> zcard(String key) {
    return new CommandObject<>(commandArguments(ZCARD).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Double> zscore(String key, String member) {
    return new CommandObject<>(commandArguments(ZSCORE).key(key).add(member), BuilderFactory.DOUBLE);
  }

  public final CommandObject<List<Double>> zmscore(String key, String... members) {
    return new CommandObject<>(commandArguments(ZMSCORE).key(key).addObjects((Object[]) members), BuilderFactory.DOUBLE_LIST);
  }

  public final CommandObject<Long> zcard(byte[] key) {
    return new CommandObject<>(commandArguments(ZCARD).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Double> zscore(byte[] key, byte[] member) {
    return new CommandObject<>(commandArguments(ZSCORE).key(key).add(member), BuilderFactory.DOUBLE);
  }

  public final CommandObject<List<Double>> zmscore(byte[] key, byte[]... members) {
    return new CommandObject<>(commandArguments(ZMSCORE).key(key).addObjects((Object[]) members), BuilderFactory.DOUBLE_LIST);
  }

  public final CommandObject<Tuple> zpopmax(String key) {
    return new CommandObject<>(commandArguments(ZPOPMAX).key(key), BuilderFactory.TUPLE);
  }

  public final CommandObject<List<Tuple>> zpopmax(String key, int count) {
    return new CommandObject<>(commandArguments(ZPOPMAX).key(key).add(count), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<Tuple> zpopmin(String key) {
    return new CommandObject<>(commandArguments(ZPOPMIN).key(key), BuilderFactory.TUPLE);
  }

  public final CommandObject<List<Tuple>> zpopmin(String key, int count) {
    return new CommandObject<>(commandArguments(ZPOPMIN).key(key).add(count), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<Tuple> zpopmax(byte[] key) {
    return new CommandObject<>(commandArguments(ZPOPMAX).key(key), BuilderFactory.TUPLE);
  }

  public final CommandObject<List<Tuple>> zpopmax(byte[] key, int count) {
    return new CommandObject<>(commandArguments(ZPOPMAX).key(key).add(count), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<Tuple> zpopmin(byte[] key) {
    return new CommandObject<>(commandArguments(ZPOPMIN).key(key), BuilderFactory.TUPLE);
  }

  public final CommandObject<List<Tuple>> zpopmin(byte[] key, int count) {
    return new CommandObject<>(commandArguments(ZPOPMIN).key(key).add(count), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<KeyedZSetElement> bzpopmax(double timeout, String... keys) {
    return new CommandObject<>(commandArguments(BZPOPMAX).blocking().keys((Object[]) keys).add(timeout), BuilderFactory.KEYED_ZSET_ELEMENT);
  }

  public final CommandObject<KeyedZSetElement> bzpopmin(double timeout, String... keys) {
    return new CommandObject<>(commandArguments(BZPOPMIN).blocking().keys((Object[]) keys).add(timeout), BuilderFactory.KEYED_ZSET_ELEMENT);
  }

  public final CommandObject<List<byte[]>> bzpopmax(double timeout, byte[]... keys) {
    return new CommandObject<>(commandArguments(BZPOPMAX).blocking().keys((Object[]) keys).add(timeout), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<byte[]>> bzpopmin(double timeout, byte[]... keys) {
    return new CommandObject<>(commandArguments(BZPOPMIN).blocking().keys((Object[]) keys).add(timeout), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<Long> zcount(String key, double min, double max) {
    return new CommandObject<>(commandArguments(ZCOUNT).key(key).add(min).add(max), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zcount(String key, String min, String max) {
    return new CommandObject<>(commandArguments(ZCOUNT).key(key).add(min).add(max), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zcount(byte[] key, double min, double max) {
    return new CommandObject<>(commandArguments(ZCOUNT).key(key).add(min).add(max), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zcount(byte[] key, byte[] min, byte[] max) {
    return new CommandObject<>(commandArguments(ZCOUNT).key(key).add(min).add(max), BuilderFactory.LONG);
  }

  public final CommandObject<List<String>> zrange(String key, long start, long stop) {
    return new CommandObject<>(commandArguments(ZRANGE).key(key).add(start).add(stop), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<List<String>> zrevrange(String key, long start, long stop) {
    return new CommandObject<>(commandArguments(ZREVRANGE).key(key).add(start).add(stop), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<List<Tuple>> zrangeWithScores(String key, long start, long stop) {
    return new CommandObject<>(commandArguments(ZRANGE).key(key)
        .add(start).add(stop).add(WITHSCORES), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<List<Tuple>> zrevrangeWithScores(String key, long start, long stop) {
    return new CommandObject<>(commandArguments(ZREVRANGE).key(key)
        .add(start).add(stop).add(WITHSCORES), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<List<String>> zrangeByScore(String key, double min, double max) {
    return new CommandObject<>(commandArguments(ZRANGEBYSCORE).key(key).add(min).add(max), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<List<String>> zrangeByScore(String key, String min, String max) {
    return new CommandObject<>(commandArguments(ZRANGEBYSCORE).key(key).add(min).add(max), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<List<String>> zrevrangeByScore(String key, double max, double min) {
    return new CommandObject<>(commandArguments(ZREVRANGEBYSCORE).key(key).add(max).add(min), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<List<String>> zrevrangeByScore(String key, String max, String min) {
    return new CommandObject<>(commandArguments(ZREVRANGEBYSCORE).key(key).add(max).add(min), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<List<String>> zrangeByScore(String key, double min, double max, int offset, int count) {
    return new CommandObject<>(commandArguments(ZRANGEBYSCORE).key(key).add(min).add(max)
        .add(LIMIT).add(offset).add(count), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<List<String>> zrangeByScore(String key, String min, String max, int offset, int count) {
    return new CommandObject<>(commandArguments(ZRANGEBYSCORE).key(key).add(min).add(max)
        .add(LIMIT).add(offset).add(count), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<List<String>> zrevrangeByScore(String key, double max, double min, int offset, int count) {
    return new CommandObject<>(commandArguments(ZREVRANGEBYSCORE).key(key).add(max).add(min)
        .add(LIMIT).add(offset).add(count), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<List<String>> zrevrangeByScore(String key, String max, String min, int offset, int count) {
    return new CommandObject<>(commandArguments(ZRANGEBYSCORE).key(key).add(max).add(min)
        .add(LIMIT).add(offset).add(count), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<List<Tuple>> zrangeByScoreWithScores(String key, double min, double max) {
    return new CommandObject<>(commandArguments(ZRANGEBYSCORE).key(key).add(min).add(max)
        .add(WITHSCORES), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<List<Tuple>> zrangeByScoreWithScores(String key, String min, String max) {
    return new CommandObject<>(commandArguments(ZRANGEBYSCORE).key(key).add(min).add(max)
        .add(WITHSCORES), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<List<Tuple>> zrevrangeByScoreWithScores(String key, double max, double min) {
    return new CommandObject<>(commandArguments(ZREVRANGEBYSCORE).key(key).add(max).add(min)
        .add(WITHSCORES), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<List<Tuple>> zrevrangeByScoreWithScores(String key, String max, String min) {
    return new CommandObject<>(commandArguments(ZREVRANGEBYSCORE).key(key).add(max).add(min)
        .add(WITHSCORES), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<List<Tuple>> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
    return new CommandObject<>(commandArguments(ZRANGEBYSCORE).key(key).add(min).add(max)
        .add(LIMIT).add(offset).add(count).add(WITHSCORES), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<List<Tuple>> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
    return new CommandObject<>(commandArguments(ZRANGEBYSCORE).key(key).add(min).add(max)
        .add(LIMIT).add(offset).add(count).add(WITHSCORES), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<List<Tuple>> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
    return new CommandObject<>(commandArguments(ZREVRANGEBYSCORE).key(key).add(max).add(min)
        .add(LIMIT).add(offset).add(count).add(WITHSCORES), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<List<Tuple>> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
    return new CommandObject<>(commandArguments(ZREVRANGEBYSCORE).key(key).add(max).add(min)
        .add(LIMIT).add(offset).add(count).add(WITHSCORES), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<List<byte[]>> zrange(byte[] key, long start, long stop) {
    return new CommandObject<>(commandArguments(ZRANGE).key(key).add(start).add(stop), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<byte[]>> zrevrange(byte[] key, long start, long stop) {
    return new CommandObject<>(commandArguments(ZREVRANGE).key(key).add(start).add(stop), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<Tuple>> zrangeWithScores(byte[] key, long start, long stop) {
    return new CommandObject<>(commandArguments(ZRANGE).key(key)
        .add(start).add(stop).add(WITHSCORES), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<List<Tuple>> zrevrangeWithScores(byte[] key, long start, long stop) {
    return new CommandObject<>(commandArguments(ZREVRANGE).key(key)
        .add(start).add(stop).add(WITHSCORES), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<List<byte[]>> zrangeByScore(byte[] key, double min, double max) {
    return new CommandObject<>(commandArguments(ZRANGEBYSCORE).key(key).add(min).add(max), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<byte[]>> zrangeByScore(byte[] key, byte[] min, byte[] max) {
    return new CommandObject<>(commandArguments(ZRANGEBYSCORE).key(key).add(min).add(max), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<byte[]>> zrevrangeByScore(byte[] key, double max, double min) {
    return new CommandObject<>(commandArguments(ZREVRANGEBYSCORE).key(key).add(max).add(min), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<byte[]>> zrevrangeByScore(byte[] key, byte[] max, byte[] min) {
    return new CommandObject<>(commandArguments(ZREVRANGEBYSCORE).key(key).add(max).add(min), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<byte[]>> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
    return new CommandObject<>(commandArguments(ZRANGEBYSCORE).key(key).add(min).add(max)
        .add(LIMIT).add(offset).add(count), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<byte[]>> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count) {
    return new CommandObject<>(commandArguments(ZRANGEBYSCORE).key(key).add(min).add(max)
        .add(LIMIT).add(offset).add(count), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<byte[]>> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {
    return new CommandObject<>(commandArguments(ZREVRANGEBYSCORE).key(key).add(max).add(min)
        .add(LIMIT).add(offset).add(count), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<byte[]>> zrevrangeByScore(byte[] key, byte[] max, byte[] min, int offset, int count) {
    return new CommandObject<>(commandArguments(ZREVRANGEBYSCORE).key(key).add(max).add(min)
        .add(LIMIT).add(offset).add(count), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<Tuple>> zrangeByScoreWithScores(byte[] key, double min, double max) {
    return new CommandObject<>(commandArguments(ZRANGEBYSCORE).key(key).add(min).add(max)
        .add(WITHSCORES), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<List<Tuple>> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max) {
    return new CommandObject<>(commandArguments(ZRANGEBYSCORE).key(key).add(min).add(max)
        .add(WITHSCORES), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<List<Tuple>> zrevrangeByScoreWithScores(byte[] key, double max, double min) {
    return new CommandObject<>(commandArguments(ZREVRANGEBYSCORE).key(key).add(max).add(min)
        .add(WITHSCORES), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<List<Tuple>> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min) {
    return new CommandObject<>(commandArguments(ZREVRANGEBYSCORE).key(key).add(max).add(min)
        .add(WITHSCORES), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<List<Tuple>> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count) {
    return new CommandObject<>(commandArguments(ZRANGEBYSCORE).key(key).add(min).add(max)
        .add(LIMIT).add(offset).add(count).add(WITHSCORES), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<List<Tuple>> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max, int offset, int count) {
    return new CommandObject<>(commandArguments(ZRANGEBYSCORE).key(key).add(min).add(max)
        .add(LIMIT).add(offset).add(count).add(WITHSCORES), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<List<Tuple>> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset, int count) {
    return new CommandObject<>(commandArguments(ZREVRANGEBYSCORE).key(key).add(max).add(min)
        .add(LIMIT).add(offset).add(count).add(WITHSCORES), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<List<Tuple>> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min, int offset, int count) {
    return new CommandObject<>(commandArguments(ZREVRANGEBYSCORE).key(key).add(max).add(min)
        .add(LIMIT).add(offset).add(count).add(WITHSCORES), BuilderFactory.TUPLE_LIST);
  }

  public final CommandObject<Long> zremrangeByRank(String key, long start, long stop) {
    return new CommandObject<>(commandArguments(ZREMRANGEBYRANK).key(key).add(start).add(stop), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zremrangeByScore(String key, double min, double max) {
    return new CommandObject<>(commandArguments(ZREMRANGEBYSCORE).key(key).add(min).add(max), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zremrangeByScore(String key, String min, String max) {
    return new CommandObject<>(commandArguments(ZREMRANGEBYSCORE).key(key).add(min).add(max), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zremrangeByRank(byte[] key, long start, long stop) {
    return new CommandObject<>(commandArguments(ZREMRANGEBYRANK).key(key).add(start).add(stop), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zremrangeByScore(byte[] key, double min, double max) {
    return new CommandObject<>(commandArguments(ZREMRANGEBYSCORE).key(key).add(min).add(max), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zremrangeByScore(byte[] key, byte[] min, byte[] max) {
    return new CommandObject<>(commandArguments(ZREMRANGEBYSCORE).key(key).add(min).add(max), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zlexcount(String key, String min, String max) {
    return new CommandObject<>(commandArguments(ZLEXCOUNT).key(key).add(min).add(max), BuilderFactory.LONG);
  }

  public final CommandObject<List<String>> zrangeByLex(String key, String min, String max) {
    return new CommandObject<>(commandArguments(ZRANGEBYLEX).key(key).add(min).add(max), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<List<String>> zrangeByLex(String key, String min, String max, int offset, int count) {
    return new CommandObject<>(commandArguments(ZRANGEBYLEX).key(key).add(min).add(max)
        .add(LIMIT).add(offset).add(count), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<List<String>> zrevrangeByLex(String key, String max, String min) {
    return new CommandObject<>(commandArguments(ZREVRANGEBYLEX).key(key).add(max).add(min), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<List<String>> zrevrangeByLex(String key, String max, String min, int offset, int count) {
    return new CommandObject<>(commandArguments(ZREVRANGEBYLEX).key(key).add(max).add(min)
        .add(LIMIT).add(offset).add(count), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<Long> zremrangeByLex(String key, String min, String max) {
    return new CommandObject<>(commandArguments(ZREMRANGEBYLEX).key(key).add(min).add(max), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zlexcount(byte[] key, byte[] min, byte[] max) {
    return new CommandObject<>(commandArguments(ZLEXCOUNT).key(key).add(min).add(max), BuilderFactory.LONG);
  }

  public final CommandObject<List<byte[]>> zrangeByLex(byte[] key, byte[] min, byte[] max) {
    return new CommandObject<>(commandArguments(ZRANGEBYLEX).key(key).add(min).add(max), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<byte[]>> zrangeByLex(byte[] key, byte[] min, byte[] max, int offset, int count) {
    return new CommandObject<>(commandArguments(ZRANGEBYLEX).key(key).add(min).add(max)
        .add(LIMIT).add(offset).add(count), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<byte[]>> zrevrangeByLex(byte[] key, byte[] max, byte[] min) {
    return new CommandObject<>(commandArguments(ZREVRANGEBYLEX).key(key).add(max).add(min), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<byte[]>> zrevrangeByLex(byte[] key, byte[] max, byte[] min, int offset, int count) {
    return new CommandObject<>(commandArguments(ZREVRANGEBYLEX).key(key).add(max).add(min)
        .add(LIMIT).add(offset).add(count), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<Long> zremrangeByLex(byte[] key, byte[] min, byte[] max) {
    return new CommandObject<>(commandArguments(ZREMRANGEBYLEX).key(key).add(min).add(max), BuilderFactory.LONG);
  }

  public final CommandObject<ScanResult<Tuple>> zscan(String key, String cursor, ScanParams params) {
    return new CommandObject<>(commandArguments(ZSCAN).key(key).add(cursor).addParams(params), BuilderFactory.ZSCAN_RESPONSE);
  }

  public final CommandObject<ScanResult<Tuple>> zscan(byte[] key, byte[] cursor, ScanParams params) {
    return new CommandObject<>(commandArguments(ZSCAN).key(key).add(cursor).addParams(params), BuilderFactory.ZSCAN_RESPONSE);
  }

  public final CommandObject<Set<String>> zdiff(String... keys) {
    return new CommandObject<>(commandArguments(ZDIFF).add(keys.length).keys((Object[]) keys), BuilderFactory.STRING_SET);
  }

  public final CommandObject<Set<Tuple>> zdiffWithScores(String... keys) {
    return new CommandObject<>(commandArguments(ZDIFF).add(keys.length).keys((Object[]) keys)
        .add(WITHSCORES), BuilderFactory.TUPLE_ZSET);
  }

  public final CommandObject<Long> zdiffStore(String dstkey, String... keys) {
    return new CommandObject<>(commandArguments(ZDIFFSTORE).key(dstkey).add(keys.length).keys((Object[]) keys), BuilderFactory.LONG);
  }

  public final CommandObject<Set<byte[]>> zdiff(byte[]... keys) {
    return new CommandObject<>(commandArguments(ZDIFF).add(keys.length).keys((Object[]) keys), BuilderFactory.BINARY_SET);
  }

  public final CommandObject<Set<Tuple>> zdiffWithScores(byte[]... keys) {
    return new CommandObject<>(commandArguments(ZDIFF).add(keys.length).keys((Object[]) keys)
        .add(WITHSCORES), BuilderFactory.TUPLE_ZSET);
  }

  public final CommandObject<Long> zdiffStore(byte[] dstkey, byte[]... keys) {
    return new CommandObject<>(commandArguments(ZDIFFSTORE).key(dstkey)
        .add(keys.length).keys((Object[]) keys), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zinterstore(String dstkey, String... sets) {
    return new CommandObject<>(commandArguments(ZINTERSTORE).key(dstkey)
        .add(sets.length).keys((Object[]) sets), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zinterstore(String dstkey, ZParams params, String... sets) {
    return new CommandObject<>(commandArguments(ZINTERSTORE).key(dstkey)
        .add(sets.length).keys((Object[]) sets).addParams(params), BuilderFactory.LONG);
  }

  public final CommandObject<Set<String>> zinter(ZParams params, String... keys) {
    return new CommandObject<>(commandArguments(ZINTER).add(keys.length).keys((Object[]) keys)
        .addParams(params), BuilderFactory.STRING_SET);
  }

  public final CommandObject<Set<Tuple>> zinterWithScores(ZParams params, String... keys) {
    return new CommandObject<>(commandArguments(ZINTER).add(keys.length).keys((Object[]) keys)
        .addParams(params).add(WITHSCORES), BuilderFactory.TUPLE_ZSET);
  }

  public final CommandObject<Long> zinterstore(byte[] dstkey, byte[]... sets) {
    return new CommandObject<>(commandArguments(ZINTERSTORE).key(dstkey)
        .add(sets.length).keys((Object[]) sets), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zinterstore(byte[] dstkey, ZParams params, byte[]... sets) {
    return new CommandObject<>(commandArguments(ZINTERSTORE).key(dstkey)
        .add(sets.length).keys((Object[]) sets).addParams(params), BuilderFactory.LONG);
  }

  public final CommandObject<Set<byte[]>> zinter(ZParams params, byte[]... keys) {
    return new CommandObject<>(commandArguments(ZINTER).add(keys.length).keys((Object[]) keys)
        .addParams(params), BuilderFactory.BINARY_SET);
  }

  public final CommandObject<Set<Tuple>> zinterWithScores(ZParams params, byte[]... keys) {
    return new CommandObject<>(commandArguments(ZINTER).add(keys.length).keys((Object[]) keys)
        .addParams(params).add(WITHSCORES), BuilderFactory.TUPLE_ZSET);
  }

  public final CommandObject<Long> zunionstore(String dstkey, String... sets) {
    return new CommandObject<>(commandArguments(ZUNIONSTORE).key(dstkey)
        .add(sets.length).keys((Object[]) sets), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zunionstore(String dstkey, ZParams params, String... sets) {
    return new CommandObject<>(commandArguments(ZUNIONSTORE).key(dstkey)
        .add(sets.length).keys((Object[]) sets).addParams(params), BuilderFactory.LONG);
  }

  public final CommandObject<Set<String>> zunion(ZParams params, String... keys) {
    return new CommandObject<>(commandArguments(ZUNION).add(keys.length).keys((Object[]) keys)
        .addParams(params), BuilderFactory.STRING_SET);
  }

  public final CommandObject<Set<Tuple>> zunionWithScores(ZParams params, String... keys) {
    return new CommandObject<>(commandArguments(ZUNION).add(keys.length).keys((Object[]) keys)
        .addParams(params).add(WITHSCORES), BuilderFactory.TUPLE_ZSET);
  }

  public final CommandObject<Long> zunionstore(byte[] dstkey, byte[]... sets) {
    return new CommandObject<>(commandArguments(ZUNIONSTORE).key(dstkey)
        .add(sets.length).keys((Object[]) sets), BuilderFactory.LONG);
  }

  public final CommandObject<Long> zunionstore(byte[] dstkey, ZParams params, byte[]... sets) {
    return new CommandObject<>(commandArguments(ZUNIONSTORE).key(dstkey)
        .add(sets.length).keys((Object[]) sets).addParams(params), BuilderFactory.LONG);
  }

  public final CommandObject<Set<byte[]>> zunion(ZParams params, byte[]... keys) {
    return new CommandObject<>(commandArguments(ZUNION).add(keys.length).keys((Object[]) keys)
        .addParams(params), BuilderFactory.BINARY_SET);
  }

  public final CommandObject<Set<Tuple>> zunionWithScores(ZParams params, byte[]... keys) {
    return new CommandObject<>(commandArguments(ZUNION).add(keys.length).keys((Object[]) keys)
        .addParams(params).add(WITHSCORES), BuilderFactory.TUPLE_ZSET);
  }
  // Sorted Set commands

  // Geo commands
  public final CommandObject<Long> geoadd(String key, double longitude, double latitude, String member) {
    return new CommandObject<>(commandArguments(GEOADD).key(key).add(longitude).add(latitude).add(member), BuilderFactory.LONG);
  }

  public final CommandObject<Long> geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
    return new CommandObject<>(addGeoCoordinateFlatMapArgs(commandArguments(GEOADD).key(key), memberCoordinateMap), BuilderFactory.LONG);
  }

  public final CommandObject<Long> geoadd(String key, GeoAddParams params, Map<String, GeoCoordinate> memberCoordinateMap) {
    return new CommandObject<>(addGeoCoordinateFlatMapArgs(commandArguments(GEOADD).key(key).addParams(params), memberCoordinateMap), BuilderFactory.LONG);
  }

  public final CommandObject<Double> geodist(String key, String member1, String member2) {
    return new CommandObject<>(commandArguments(GEODIST).key(key).add(member1).add(member2), BuilderFactory.DOUBLE);
  }

  public final CommandObject<Double> geodist(String key, String member1, String member2, GeoUnit unit) {
    return new CommandObject<>(commandArguments(GEODIST).key(key).add(member1).add(member2).add(unit), BuilderFactory.DOUBLE);
  }

  public final CommandObject<List<String>> geohash(String key, String... members) {
    return new CommandObject<>(commandArguments(GEOHASH).key(key).addObjects((Object[]) members), BuilderFactory.STRING_LIST);
  }

  public final CommandObject<List<GeoCoordinate>> geopos(String key, String... members) {
    return new CommandObject<>(commandArguments(GEOPOS).key(key).addObjects((Object[]) members), BuilderFactory.GEO_COORDINATE_LIST);
  }

  public final CommandObject<Long> geoadd(byte[] key, double longitude, double latitude, byte[] member) {
    return new CommandObject<>(commandArguments(GEOADD).key(key).add(longitude).add(latitude).add(member), BuilderFactory.LONG);
  }

  public final CommandObject<Long> geoadd(byte[] key, Map<byte[], GeoCoordinate> memberCoordinateMap) {
    return new CommandObject<>(addGeoCoordinateFlatMapArgs(commandArguments(GEOADD).key(key), memberCoordinateMap), BuilderFactory.LONG);
  }

  public final CommandObject<Long> geoadd(byte[] key, GeoAddParams params, Map<byte[], GeoCoordinate> memberCoordinateMap) {
    return new CommandObject<>(addGeoCoordinateFlatMapArgs(commandArguments(GEOADD).key(key).addParams(params), memberCoordinateMap), BuilderFactory.LONG);
  }

  public final CommandObject<Double> geodist(byte[] key, byte[] member1, byte[] member2) {
    return new CommandObject<>(commandArguments(GEODIST).key(key).add(member1).add(member2), BuilderFactory.DOUBLE);
  }

  public final CommandObject<Double> geodist(byte[] key, byte[] member1, byte[] member2, GeoUnit unit) {
    return new CommandObject<>(commandArguments(GEODIST).key(key).add(member1).add(member2).add(unit), BuilderFactory.DOUBLE);
  }

  public final CommandObject<List<byte[]>> geohash(byte[] key, byte[]... members) {
    return new CommandObject<>(commandArguments(GEOHASH).key(key).addObjects((Object[]) members), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<GeoCoordinate>> geopos(byte[] key, byte[]... members) {
    return new CommandObject<>(commandArguments(GEOPOS).key(key).addObjects((Object[]) members), BuilderFactory.GEO_COORDINATE_LIST);
  }

  public final CommandObject<List<GeoRadiusResponse>> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit) {
    return new CommandObject<>(commandArguments(GEORADIUS).key(key).add(longitude).add(latitude)
        .add(radius).add(unit), BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT);
  }

  public final CommandObject<List<GeoRadiusResponse>> georadius(String key,
      double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
    return new CommandObject<>(commandArguments(GEORADIUS).key(key).add(longitude).add(latitude)
        .add(radius).add(unit).addParams(param), BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT);
  }

  public final CommandObject<List<GeoRadiusResponse>> georadiusReadonly(String key, double longitude, double latitude, double radius, GeoUnit unit) {
    return new CommandObject<>(commandArguments(GEORADIUS_RO).key(key).add(longitude).add(latitude)
        .add(radius).add(unit), BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT);
  }

  public final CommandObject<List<GeoRadiusResponse>> georadiusReadonly(String key,
      double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
    return new CommandObject<>(commandArguments(GEORADIUS_RO).key(key).add(longitude).add(latitude)
        .add(radius).add(unit).addParams(param), BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT);
  }

  public final CommandObject<Long> georadiusStore(String key, double longitude, double latitude,
      double radius, GeoUnit unit, GeoRadiusParam param, GeoRadiusStoreParam storeParam) {
    return new CommandObject<>(commandArguments(GEORADIUS).key(key).add(longitude).add(latitude)
        .add(radius).add(unit).addParams(param).addParams(storeParam), BuilderFactory.LONG);
  }

  public final CommandObject<List<GeoRadiusResponse>> georadiusByMember(String key, String member, double radius, GeoUnit unit) {
    return new CommandObject<>(commandArguments(GEORADIUSBYMEMBER).key(key).add(member)
        .add(radius).add(unit), BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT);
  }

  public final CommandObject<List<GeoRadiusResponse>> georadiusByMember(String key,
      String member, double radius, GeoUnit unit, GeoRadiusParam param) {
    return new CommandObject<>(commandArguments(GEORADIUSBYMEMBER).key(key).add(member)
        .add(radius).add(unit).addParams(param), BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT);
  }

  public final CommandObject<List<GeoRadiusResponse>> georadiusByMemberReadonly(String key, String member, double radius, GeoUnit unit) {
    return new CommandObject<>(commandArguments(GEORADIUSBYMEMBER_RO).key(key).add(member)
        .add(radius).add(unit), BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT);
  }

  public final CommandObject<List<GeoRadiusResponse>> georadiusByMemberReadonly(String key,
      String member, double radius, GeoUnit unit, GeoRadiusParam param) {
    return new CommandObject<>(commandArguments(GEORADIUSBYMEMBER_RO).key(key).add(member)
        .add(radius).add(unit).addParams(param), BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT);
  }

  public final CommandObject<Long> georadiusByMemberStore(String key, String member,
      double radius, GeoUnit unit, GeoRadiusParam param, GeoRadiusStoreParam storeParam) {
    return new CommandObject<>(commandArguments(GEORADIUSBYMEMBER).key(key).add(member)
        .add(radius).add(unit).addParams(param).addParams(storeParam), BuilderFactory.LONG);
  }

  public final CommandObject<List<GeoRadiusResponse>> georadius(byte[] key, double longitude, double latitude, double radius, GeoUnit unit) {
    return new CommandObject<>(commandArguments(GEORADIUS).key(key).add(longitude).add(latitude)
        .add(radius).add(unit), BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT);
  }

  public final CommandObject<List<GeoRadiusResponse>> georadius(byte[] key,
      double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
    return new CommandObject<>(commandArguments(GEORADIUS).key(key).add(longitude).add(latitude)
        .add(radius).add(unit).addParams(param), BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT);
  }

  public final CommandObject<List<GeoRadiusResponse>> georadiusReadonly(byte[] key,
      double longitude, double latitude, double radius, GeoUnit unit) {
    return new CommandObject<>(commandArguments(GEORADIUS_RO).key(key).add(longitude).add(latitude)
        .add(radius).add(unit), BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT);
  }

  public final CommandObject<List<GeoRadiusResponse>> georadiusReadonly(byte[] key,
      double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
    return new CommandObject<>(commandArguments(GEORADIUS_RO).key(key).add(longitude).add(latitude)
        .add(radius).add(unit).addParams(param), BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT);
  }

  public final CommandObject<Long> georadiusStore(byte[] key, double longitude, double latitude,
      double radius, GeoUnit unit, GeoRadiusParam param, GeoRadiusStoreParam storeParam) {
    return new CommandObject<>(commandArguments(GEORADIUS).key(key).add(longitude).add(latitude)
        .add(radius).add(unit).addParams(param).addParams(storeParam), BuilderFactory.LONG);
  }

  public final CommandObject<List<GeoRadiusResponse>> georadiusByMember(byte[] key, byte[] member, double radius, GeoUnit unit) {
    return new CommandObject<>(commandArguments(GEORADIUSBYMEMBER).key(key).add(member)
        .add(radius).add(unit), BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT);
  }

  public final CommandObject<List<GeoRadiusResponse>> georadiusByMember(byte[] key, byte[] member, double radius, GeoUnit unit, GeoRadiusParam param) {
    return new CommandObject<>(commandArguments(GEORADIUSBYMEMBER).key(key).add(member)
        .add(radius).add(unit).addParams(param), BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT);
  }

  public final CommandObject<List<GeoRadiusResponse>> georadiusByMemberReadonly(byte[] key, byte[] member, double radius, GeoUnit unit) {
    return new CommandObject<>(commandArguments(GEORADIUSBYMEMBER_RO).key(key).add(member)
        .add(radius).add(unit), BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT);
  }

  public final CommandObject<List<GeoRadiusResponse>> georadiusByMemberReadonly(byte[] key, byte[] member, double radius, GeoUnit unit, GeoRadiusParam param) {
    return new CommandObject<>(commandArguments(GEORADIUSBYMEMBER_RO).key(key).add(member)
        .add(radius).add(unit).addParams(param), BuilderFactory.GEORADIUS_WITH_PARAMS_RESULT);
  }

  public final CommandObject<Long> georadiusByMemberStore(byte[] key, byte[] member,
      double radius, GeoUnit unit, GeoRadiusParam param, GeoRadiusStoreParam storeParam) {
    return new CommandObject<>(commandArguments(GEORADIUSBYMEMBER).key(key).add(member)
        .add(radius).add(unit).addParams(param).addParams(storeParam), BuilderFactory.LONG);
  }
  // Geo commands

  // Hyper Log Log commands
  public final CommandObject<Long> pfadd(String key, String... elements) {
    return new CommandObject<>(commandArguments(PFADD).key(key).addObjects((Object[]) elements), BuilderFactory.LONG);
  }

  public final CommandObject<String> pfmerge(String destkey, String... sourcekeys) {
    return new CommandObject<>(commandArguments(PFMERGE).key(destkey).keys((Object[]) sourcekeys), BuilderFactory.STRING);
  }

  public final CommandObject<Long> pfadd(byte[] key, byte[]... elements) {
    return new CommandObject<>(commandArguments(PFADD).key(key).addObjects((Object[]) elements), BuilderFactory.LONG);
  }

  public final CommandObject<String> pfmerge(byte[] destkey, byte[]... sourcekeys) {
    return new CommandObject<>(commandArguments(PFMERGE).key(destkey).keys((Object[]) sourcekeys), BuilderFactory.STRING);
  }

  public final CommandObject<Long> pfcount(String key) {
    return new CommandObject<>(commandArguments(PFCOUNT).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> pfcount(String... keys) {
    return new CommandObject<>(commandArguments(PFCOUNT).keys((Object[]) keys), BuilderFactory.LONG);
  }

  public final CommandObject<Long> pfcount(byte[] key) {
    return new CommandObject<>(commandArguments(PFCOUNT).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> pfcount(byte[]... keys) {
    return new CommandObject<>(commandArguments(PFCOUNT).keys((Object[]) keys), BuilderFactory.LONG);
  }
  // Hyper Log Log commands

  // Stream commands
  public final CommandObject<StreamEntryID> xadd(String key, StreamEntryID id, Map<String, String> hash) {
    return new CommandObject<>(addFlatMapArgs(commandArguments(XADD).key(key).add(id == null ? StreamEntryID.NEW_ENTRY : id), hash),
        BuilderFactory.STREAM_ENTRY_ID);
  }

  public final CommandObject<StreamEntryID> xadd(String key, XAddParams params, Map<String, String> hash) {
    return new CommandObject<>(addFlatMapArgs(commandArguments(XADD).key(key).addParams(params), hash),
        BuilderFactory.STREAM_ENTRY_ID);
  }

  public final CommandObject<Long> xlen(String key) {
    return new CommandObject<>(commandArguments(XLEN).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<byte[]> xadd(byte[] key, XAddParams params, Map<byte[], byte[]> hash) {
    return new CommandObject<>(addFlatMapArgs(commandArguments(XADD).key(key).addParams(params), hash),
        BuilderFactory.BINARY);
  }

  public final CommandObject<Long> xlen(byte[] key) {
    return new CommandObject<>(commandArguments(XLEN).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<List<StreamEntry>> xrange(String key, StreamEntryID start, StreamEntryID end) {
    return new CommandObject<>(commandArguments(XRANGE).key(key).add(start == null ? "-" : start).add(end == null ? "+" : end),
        BuilderFactory.STREAM_ENTRY_LIST);
  }

  public final CommandObject<List<StreamEntry>> xrange(String key, StreamEntryID start, StreamEntryID end, int count) {
    return new CommandObject<>(commandArguments(XRANGE).key(key).add(start == null ? "-" : start).add(end == null ? "+" : end)
        .add(COUNT).add(count), BuilderFactory.STREAM_ENTRY_LIST);
  }

  public final CommandObject<List<StreamEntry>> xrevrange(String key, StreamEntryID end, StreamEntryID start) {
    return new CommandObject<>(commandArguments(XREVRANGE).key(key).add(end == null ? "+" : end).add(start == null ? "-" : start),
        BuilderFactory.STREAM_ENTRY_LIST);
  }

  public final CommandObject<List<StreamEntry>> xrevrange(String key, StreamEntryID end, StreamEntryID start, int count) {
    return new CommandObject<>(commandArguments(XREVRANGE).key(key).add(end == null ? "+" : end).add(start == null ? "-" : start)
        .add(COUNT).add(count), BuilderFactory.STREAM_ENTRY_LIST);
  }

  public final CommandObject<List<byte[]>> xrange(byte[] key, byte[] start, byte[] end) {
    return new CommandObject<>(commandArguments(XRANGE).key(key).add(start == null ? "-" : start).add(end == null ? "+" : end),
        BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<byte[]>> xrange(byte[] key, byte[] start, byte[] end, int count) {
    return new CommandObject<>(commandArguments(XRANGE).key(key).add(start == null ? "-" : start).add(end == null ? "+" : end)
        .add(COUNT).add(count), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<byte[]>> xrevrange(byte[] key, byte[] end, byte[] start) {
    return new CommandObject<>(commandArguments(XREVRANGE).key(key).add(end == null ? "+" : end).add(start == null ? "-" : start),
        BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<byte[]>> xrevrange(byte[] key, byte[] end, byte[] start, int count) {
    return new CommandObject<>(commandArguments(XREVRANGE).key(key).add(end == null ? "+" : end).add(start == null ? "-" : start)
        .add(COUNT).add(count), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<Long> xack(String key, String group, StreamEntryID... ids) {
    return new CommandObject<>(commandArguments(XACK).key(key).add(group).addObjects((Object[]) ids), BuilderFactory.LONG);
  }

  public final CommandObject<Long> xack(byte[] key, byte[] group, byte[]... ids) {
    return new CommandObject<>(commandArguments(XACK).key(key).add(group).addObjects((Object[]) ids), BuilderFactory.LONG);
  }

  public final CommandObject<String> xgroupCreate(String key, String groupname, StreamEntryID id, boolean makeStream) {
    CommandArguments args = commandArguments(XGROUP).add(CREATE).key(key)
        .add(groupname).add(id == null ? "0-0" : id);
    if (makeStream) args.add(MKSTREAM);
    return new CommandObject<>(args, BuilderFactory.STRING);
  }

  public final CommandObject<String> xgroupSetID(String key, String groupname, StreamEntryID id) {
    return new CommandObject<>(commandArguments(XGROUP).add(SETID)
        .key(key).add(groupname).add(id), BuilderFactory.STRING);
  }

  public final CommandObject<Long> xgroupDestroy(String key, String groupname) {
    return new CommandObject<>(commandArguments(XGROUP).add(DESTROY)
        .key(key).add(groupname), BuilderFactory.LONG);
  }

  public final CommandObject<Long> xgroupDelConsumer(String key, String groupname, String consumername) {
    return new CommandObject<>(commandArguments(XGROUP).add(DELCONSUMER)
        .key(key).add(groupname).add(consumername), BuilderFactory.LONG);
  }

  public final CommandObject<String> xgroupCreate(byte[] key, byte[] groupname, byte[] id, boolean makeStream) {
    CommandArguments args = commandArguments(XGROUP).add(CREATE).key(key)
        .add(groupname).add(id);
    if (makeStream) args.add(MKSTREAM);
    return new CommandObject<>(args, BuilderFactory.STRING);
  }

  public final CommandObject<String> xgroupSetID(byte[] key, byte[] groupname, byte[] id) {
    return new CommandObject<>(commandArguments(XGROUP).add(SETID)
        .key(key).add(groupname).add(id), BuilderFactory.STRING);
  }

  public final CommandObject<Long> xgroupDestroy(byte[] key, byte[] groupname) {
    return new CommandObject<>(commandArguments(XGROUP).add(DESTROY)
        .key(key).add(groupname), BuilderFactory.LONG);
  }

  public final CommandObject<Long> xgroupDelConsumer(byte[] key, byte[] groupname, byte[] consumerName) {
    return new CommandObject<>(commandArguments(XGROUP).add(DELCONSUMER)
        .key(key).add(groupname).add(consumerName), BuilderFactory.LONG);
  }

  public final CommandObject<Long> xdel(String key, StreamEntryID... ids) {
    return new CommandObject<>(commandArguments(XDEL).key(key).addObjects((Object[]) ids), BuilderFactory.LONG);
  }

  public final CommandObject<Long> xtrim(String key, long maxLen, boolean approximate) {
    CommandArguments args = commandArguments(XTRIM).key(key).add(MAXLEN);
    if (approximate) args.add(Protocol.BYTES_TILDE);
    args.add(maxLen);
    return new CommandObject<>(args, BuilderFactory.LONG);
  }

  public final CommandObject<Long> xtrim(String key, XTrimParams params) {
    return new CommandObject<>(commandArguments(XTRIM).key(key).addParams(params), BuilderFactory.LONG);
  }

  public final CommandObject<Long> xdel(byte[] key, byte[]... ids) {
    return new CommandObject<>(commandArguments(XDEL).key(key).addObjects((Object[]) ids), BuilderFactory.LONG);
  }

  public final CommandObject<Long> xtrim(byte[] key, long maxLen, boolean approximateLength) {
    CommandArguments args = commandArguments(XTRIM).key(key).add(MAXLEN);
    if (approximateLength) args.add(Protocol.BYTES_TILDE);
    args.add(maxLen);
    return new CommandObject<>(args, BuilderFactory.LONG);
  }

  public final CommandObject<Long> xtrim(byte[] key, XTrimParams params) {
    return new CommandObject<>(commandArguments(XTRIM).key(key).addParams(params), BuilderFactory.LONG);
  }

  public final CommandObject<StreamPendingSummary> xpending(String key, String groupname) {
    return new CommandObject<>(commandArguments(XPENDING).key(key).add(groupname),
        BuilderFactory.STREAM_PENDING_SUMMARY);
  }

  public final CommandObject<List<StreamPendingEntry>> xpending(String key, String groupname,
      StreamEntryID start, StreamEntryID end, int count, String consumername) {
    CommandArguments args = commandArguments(XPENDING).key(key).add(groupname)
        .add(start == null ? "-" : start).add(end == null ? "+" : end).add(count);
    if (consumername != null) args.add(consumername);
    return new CommandObject<>(args, BuilderFactory.STREAM_PENDING_ENTRY_LIST);
  }

  public final CommandObject<List<StreamPendingEntry>> xpending(String key, String groupname, XPendingParams params) {
    return new CommandObject<>(commandArguments(XPENDING).key(key).add(groupname)
        .addParams(params), BuilderFactory.STREAM_PENDING_ENTRY_LIST);
  }

  public final CommandObject<Object> xpending(byte[] key, byte[] groupname) {
    return new CommandObject<>(commandArguments(XPENDING).key(key).add(groupname),
        BuilderFactory.RAW_OBJECT);
  }

  public final CommandObject<List<Object>> xpending(byte[] key, byte[] groupname,
      byte[] start, byte[] end, int count, byte[] consumername) {
    CommandArguments args = commandArguments(XPENDING).key(key).add(groupname)
        .add(start == null ? "-" : start).add(end == null ? "+" : end).add(count);
    if (consumername != null) args.add(consumername);
    return new CommandObject<>(args, BuilderFactory.RAW_OBJECT_LIST);
  }

  public final CommandObject<List<Object>> xpending(byte[] key, byte[] groupname, XPendingParams params) {
    return new CommandObject<>(commandArguments(XPENDING).key(key).add(groupname)
        .addParams(params), BuilderFactory.RAW_OBJECT_LIST);
  }

  public final CommandObject<List<StreamEntry>> xclaim(String key, String group,
      String consumername, long minIdleTime, XClaimParams params, StreamEntryID... ids) {
    return new CommandObject<>(commandArguments(XCLAIM).key(key).add(group)
        .add(consumername).add(minIdleTime).addObjects((Object[]) ids).addParams(params),
        BuilderFactory.STREAM_ENTRY_LIST);
  }

  public final CommandObject<List<StreamEntryID>> xclaimJustId(String key, String group,
      String consumername, long minIdleTime, XClaimParams params, StreamEntryID... ids) {
    return new CommandObject<>(commandArguments(XCLAIM).key(key).add(group)
        .add(consumername).add(minIdleTime).addObjects((Object[]) ids).addParams(params)
        .add(JUSTID), BuilderFactory.STREAM_ENTRY_ID_LIST);
  }

  public final CommandObject<Map.Entry<StreamEntryID, List<StreamEntry>>> xautoclaim(String key,
      String group, String consumerName, long minIdleTime, StreamEntryID start,
      XAutoClaimParams params) {
    return new CommandObject<>(commandArguments(XAUTOCLAIM).key(key).add(group)
        .add(consumerName).add(minIdleTime).add(start).addParams(params),
        BuilderFactory.STREAM_AUTO_CLAIM_RESPONSE);
  }

  public final CommandObject<Map.Entry<StreamEntryID, List<StreamEntryID>>> xautoclaimJustId(
      String key, String group, String consumerName, long minIdleTime, StreamEntryID start,
      XAutoClaimParams params) {
    return new CommandObject<>(commandArguments(XAUTOCLAIM).key(key).add(group)
        .add(consumerName).add(minIdleTime).add(start).addParams(params)
        .add(JUSTID), BuilderFactory.STREAM_AUTO_CLAIM_ID_RESPONSE);
  }

  public final CommandObject<List<byte[]>> xclaim(byte[] key, byte[] group,
      byte[] consumername, long minIdleTime, XClaimParams params, byte[]... ids) {
    return new CommandObject<>(commandArguments(XCLAIM).key(key).add(group)
        .add(consumername).add(minIdleTime).addObjects((Object[]) ids).addParams(params),
        BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<byte[]>> xclaimJustId(byte[] key, byte[] group,
      byte[] consumername, long minIdleTime, XClaimParams params, byte[]... ids) {
    return new CommandObject<>(commandArguments(XCLAIM).key(key).add(group)
        .add(consumername).add(minIdleTime).addObjects((Object[]) ids).addParams(params)
        .add(JUSTID), BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<Object>> xautoclaim(byte[] key, byte[] groupName,
      byte[] consumerName, long minIdleTime, byte[] start, XAutoClaimParams params) {
    return new CommandObject<>(commandArguments(XAUTOCLAIM).key(key).add(groupName)
        .add(consumerName).add(minIdleTime).add(start).addParams(params),
        BuilderFactory.RAW_OBJECT_LIST);
  }

  public final CommandObject<List<Object>> xautoclaimJustId(byte[] key, byte[] groupName,
      byte[] consumerName, long minIdleTime, byte[] start, XAutoClaimParams params) {
    return new CommandObject<>(commandArguments(XAUTOCLAIM).key(key).add(groupName)
        .add(consumerName).add(minIdleTime).add(start).addParams(params)
        .add(JUSTID), BuilderFactory.RAW_OBJECT_LIST);
  }

  public final CommandObject<StreamInfo> xinfoStream(String key) {
    return new CommandObject<>(commandArguments(XINFO).add(STREAM).key(key), BuilderFactory.STREAM_INFO);
  }

  public final CommandObject<List<StreamGroupInfo>> xinfoGroup(String key) {
    return new CommandObject<>(commandArguments(XINFO).add(GROUPS).key(key), BuilderFactory.STREAM_GROUP_INFO_LIST);
  }

  public final CommandObject<List<StreamConsumersInfo>> xinfoConsumers(String key, String group) {
    return new CommandObject<>(commandArguments(XINFO).add(CONSUMERS).key(key).add(group), BuilderFactory.STREAM_CONSUMERS_INFO_LIST);
  }

  public final CommandObject<Object> xinfoStream(byte[] key) {
    return new CommandObject<>(commandArguments(XINFO).add(STREAM).key(key), BuilderFactory.RAW_OBJECT);
  }

  public final CommandObject<List<Object>> xinfoGroup(byte[] key) {
    return new CommandObject<>(commandArguments(XINFO).add(GROUPS).key(key), BuilderFactory.RAW_OBJECT_LIST);
  }

  public final CommandObject<List<Object>> xinfoConsumers(byte[] key, byte[] group) {
    return new CommandObject<>(commandArguments(XINFO).add(CONSUMERS).key(key).add(group), BuilderFactory.RAW_OBJECT_LIST);
  }

  public final CommandObject<List<Map.Entry<String, List<StreamEntry>>>> xread(
      XReadParams xReadParams, Map<String, StreamEntryID> streams) {
    CommandArguments args = commandArguments(XREAD).addParams(xReadParams).add(STREAMS);
    Set<Map.Entry<String, StreamEntryID>> entrySet = streams.entrySet();
    entrySet.forEach(entry -> args.key(entry.getKey()));
    entrySet.forEach(entry -> args.add(entry.getValue()));
    return new CommandObject<>(args, BuilderFactory.STREAM_READ_RESPONSE);
  }

  public final CommandObject<List<Map.Entry<String, List<StreamEntry>>>> xreadGroup(
      String groupname, String consumer, XReadGroupParams xReadGroupParams,
      Map<String, StreamEntryID> streams) {
    CommandArguments args = commandArguments(XREADGROUP)
        .add(GROUP).add(groupname).add(consumer)
        .addParams(xReadGroupParams).add(STREAMS);
    Set<Map.Entry<String, StreamEntryID>> entrySet = streams.entrySet();
    entrySet.forEach(entry -> args.key(entry.getKey()));
    entrySet.forEach(entry -> args.add(entry.getValue()));
    return new CommandObject<>(args, BuilderFactory.STREAM_READ_RESPONSE);
  }

  public final CommandObject<List<byte[]>> xread(XReadParams xReadParams, Map.Entry<byte[], byte[]>... streams) {
    CommandArguments args = commandArguments(XREAD).addParams(xReadParams).add(STREAMS);
    for (Map.Entry<byte[], byte[]> entry : streams) {
      args.key(entry.getKey());
    }
    for (Map.Entry<byte[], byte[]> entry : streams) {
      args.add(entry.getValue());
    }
    return new CommandObject<>(args, BuilderFactory.BINARY_LIST);
  }

  public final CommandObject<List<byte[]>> xreadGroup(byte[] groupname, byte[] consumer,
      XReadGroupParams xReadGroupParams, Map.Entry<byte[], byte[]>... streams) {
    CommandArguments args = commandArguments(XREADGROUP)
        .add(GROUP).add(groupname).add(consumer)
        .addParams(xReadGroupParams).add(STREAMS);
    for (Map.Entry<byte[], byte[]> entry : streams) {
      args.key(entry.getKey());
    }
    for (Map.Entry<byte[], byte[]> entry : streams) {
      args.add(entry.getValue());
    }
    return new CommandObject<>(args, BuilderFactory.BINARY_LIST);
  }
  // Stream commands

  // Scripting commands
  public final CommandObject<Object> eval(String script) {
    return new CommandObject<>(commandArguments(EVAL).add(script).add(0), BuilderFactory.ENCODED_OBJECT);
  }

  public final CommandObject<Object> eval(String script, String sampleKey) {
    return new CommandObject<>(commandArguments(EVAL).add(script).add(0).processKey(sampleKey), BuilderFactory.ENCODED_OBJECT);
  }

  public final CommandObject<Object> eval(String script, int keyCount, String... params) {
    return new CommandObject<>(commandArguments(EVAL).add(script).add(keyCount)
        .addObjects((Object[]) params).processKeys(Arrays.copyOf(params, keyCount)),
        BuilderFactory.ENCODED_OBJECT);
  }

  public final CommandObject<Object> eval(String script, List<String> keys, List<String> args) {
    String[] keysArray = keys.toArray(new String[keys.size()]);
    String[] argsArray = args.toArray(new String[args.size()]);
    return new CommandObject<>(commandArguments(EVAL).add(script).add(keysArray.length)
        .keys((Object[]) keysArray).addObjects((Object[]) argsArray),
        BuilderFactory.ENCODED_OBJECT);
  }

  public final CommandObject<Object> eval(byte[] script) {
    return new CommandObject<>(commandArguments(EVAL).add(script).add(0), BuilderFactory.RAW_OBJECT);
  }

  public final CommandObject<Object> eval(byte[] script, byte[] sampleKey) {
    return new CommandObject<>(commandArguments(EVAL).add(script).add(0).processKey(sampleKey), BuilderFactory.RAW_OBJECT);
  }

  public final CommandObject<Object> eval(byte[] script, int keyCount, byte[]... params) {
    return new CommandObject<>(commandArguments(EVAL).add(script).add(keyCount)
        .addObjects((Object[]) params).processKeys(Arrays.copyOf(params, keyCount)),
        BuilderFactory.RAW_OBJECT);
  }

  public final CommandObject<Object> eval(byte[] script, List<byte[]> keys, List<byte[]> args) {
    byte[][] keysArray = keys.toArray(new byte[keys.size()][]);
    byte[][] argsArray = args.toArray(new byte[args.size()][]);
    return new CommandObject<>(commandArguments(EVAL).add(script).add(keysArray.length)
        .keys((Object[]) keysArray).addObjects((Object[]) argsArray),
        BuilderFactory.RAW_OBJECT);
  }

  public final CommandObject<Object> evalsha(String sha1) {
    return new CommandObject<>(commandArguments(EVALSHA).add(sha1).add(0), BuilderFactory.ENCODED_OBJECT);
  }

  public final CommandObject<Object> evalsha(String sha1, String sampleKey) {
    return new CommandObject<>(commandArguments(EVALSHA).add(sha1).add(0).processKey(sampleKey), BuilderFactory.ENCODED_OBJECT);
  }

  public final CommandObject<Object> evalsha(String sha1, int keyCount, String... params) {
    return new CommandObject<>(commandArguments(EVALSHA).add(sha1).add(keyCount)
        .addObjects((Object[]) params).processKeys(Arrays.copyOf(params, keyCount)),
        BuilderFactory.ENCODED_OBJECT);
  }

  public final CommandObject<Object> evalsha(String sha1, List<String> keys, List<String> args) {
    String[] keysArray = keys.toArray(new String[keys.size()]);
    String[] argsArray = args.toArray(new String[args.size()]);
    return new CommandObject<>(commandArguments(EVALSHA).add(sha1).add(keysArray.length)
        .keys((Object[]) keysArray).addObjects((Object[]) argsArray),
        BuilderFactory.ENCODED_OBJECT);
  }

  public final CommandObject<Object> evalsha(byte[] sha1) {
    return new CommandObject<>(commandArguments(EVALSHA).add(sha1).add(0), BuilderFactory.RAW_OBJECT);
  }

  public final CommandObject<Object> evalsha(byte[] sha1, byte[] sampleKey) {
    return new CommandObject<>(commandArguments(EVALSHA).add(sha1).add(0).processKey(sampleKey), BuilderFactory.RAW_OBJECT);
  }

  public final CommandObject<Object> evalsha(byte[] sha1, int keyCount, byte[]... params) {
    return new CommandObject<>(commandArguments(EVALSHA).add(sha1).add(keyCount)
        .addObjects((Object[]) params).processKeys(Arrays.copyOf(params, keyCount)),
        BuilderFactory.RAW_OBJECT);
  }

  public final CommandObject<Object> evalsha(byte[] sha1, List<byte[]> keys, List<byte[]> args) {
    byte[][] keysArray = keys.toArray(new byte[keys.size()][]);
    byte[][] argsArray = args.toArray(new byte[args.size()][]);
    return new CommandObject<>(commandArguments(EVALSHA).add(sha1).add(keysArray.length)
        .keys((Object[]) keysArray).addObjects((Object[]) argsArray),
        BuilderFactory.RAW_OBJECT);
  }

  public final CommandObject<List<Boolean>> scriptExists(String sampleKey, String... sha1s) {
    return new CommandObject<>(commandArguments(SCRIPT).add(Keyword.EXISTS).addObjects((Object[]) sha1s)
        .processKey(sampleKey), BuilderFactory.BOOLEAN_LIST);
  }

  public final CommandObject<String> scriptLoad(String script, String sampleKey) {
    return new CommandObject<>(commandArguments(SCRIPT).add(LOAD).add(script).processKey(sampleKey), BuilderFactory.STRING);
  }

  public final CommandObject<String> scriptFlush(String sampleKey) {
    return new CommandObject<>(commandArguments(SCRIPT).add(FLUSH).processKey(sampleKey), BuilderFactory.STRING);
  }

  public final CommandObject<String> scriptFlush(String sampleKey, FlushMode flushMode) {
    return new CommandObject<>(commandArguments(SCRIPT).add(FLUSH).add(flushMode).processKey(sampleKey), BuilderFactory.STRING);
  }

  public final CommandObject<String> scriptKill(String sampleKey) {
    return new CommandObject<>(commandArguments(SCRIPT).add(KILL).processKey(sampleKey), BuilderFactory.STRING);
  }

  public final CommandObject<List<Boolean>> scriptExists(byte[] sampleKey, byte[]... sha1s) {
    return new CommandObject<>(commandArguments(SCRIPT).add(Keyword.EXISTS).addObjects((Object[]) sha1s)
        .processKey(sampleKey), BuilderFactory.BOOLEAN_LIST);
  }

  public final CommandObject<byte[]> scriptLoad(byte[] script, byte[] sampleKey) {
    return new CommandObject<>(commandArguments(SCRIPT).add(LOAD).add(script).processKey(sampleKey), BuilderFactory.BINARY);
  }

  public final CommandObject<String> scriptFlush(byte[] sampleKey) {
    return new CommandObject<>(commandArguments(SCRIPT).add(FLUSH).processKey(sampleKey), BuilderFactory.STRING);
  }

  public final CommandObject<String> scriptFlush(byte[] sampleKey, FlushMode flushMode) {
    return new CommandObject<>(commandArguments(SCRIPT).add(FLUSH).add(flushMode).processKey(sampleKey), BuilderFactory.STRING);
  }

  public final CommandObject<String> scriptKill(byte[] sampleKey) {
    return new CommandObject<>(commandArguments(SCRIPT).add(KILL).processKey(sampleKey), BuilderFactory.STRING);
  }
  // Scripting commands

  // Miscellaneous commands
  public final CommandObject<LCSMatchResult> strAlgoLCSStrings(String strA, String strB, StrAlgoLCSParams params) {
    return new CommandObject<>(commandArguments(STRALGO).add(LCS).add(STRINGS)
        .add(strA).add(strB).addParams(params),
        BuilderFactory.STR_ALGO_LCS_RESULT_BUILDER);
  }

  public final CommandObject<LCSMatchResult> strAlgoLCSStrings(byte[] strA, byte[] strB, StrAlgoLCSParams params) {
    return new CommandObject<>(commandArguments(STRALGO).add(LCS).add(STRINGS)
        .add(strA).add(strB).addParams(params),
        BuilderFactory.STR_ALGO_LCS_RESULT_BUILDER);
  }

  public final CommandObject<Boolean> copy(String srcKey, String dstKey, int dstDB, boolean replace) {
    CommandArguments args = commandArguments(Command.COPY).key(srcKey).key(dstKey).add(DB).add(dstDB);
    if (replace) {
      args.add(REPLACE);
    }
    return new CommandObject<>(args, BuilderFactory.BOOLEAN);
  }

  public final CommandObject<Boolean> copy(byte[] srcKey, byte[] dstKey, int dstDB, boolean replace) {
    CommandArguments args = commandArguments(Command.COPY).key(srcKey).key(dstKey).add(DB).add(dstDB);
    if (replace) {
      args.add(REPLACE);
    }
    return new CommandObject<>(args, BuilderFactory.BOOLEAN);
  }

  public final CommandObject<Long> memoryUsage(String key) {
    return new CommandObject<>(commandArguments(MEMORY).add(USAGE).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> memoryUsage(String key, int samples) {
    return new CommandObject<>(commandArguments(MEMORY).add(USAGE).key(key).add(SAMPLES).add(samples), BuilderFactory.LONG);
  }

  public final CommandObject<Long> memoryUsage(byte[] key) {
    return new CommandObject<>(commandArguments(MEMORY).add(USAGE).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> memoryUsage(byte[] key, int samples) {
    return new CommandObject<>(commandArguments(MEMORY).add(USAGE).key(key).add(samples), BuilderFactory.LONG);
  }

  public final CommandObject<Long> objectRefcount(String key) {
    return new CommandObject<>(commandArguments(OBJECT).add(REFCOUNT).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<String> objectEncoding(String key) {
    return new CommandObject<>(commandArguments(OBJECT).add(ENCODING).key(key), BuilderFactory.STRING);
  }

  public final CommandObject<Long> objectIdletime(String key) {
    return new CommandObject<>(commandArguments(OBJECT).add(IDLETIME).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> objectFreq(String key) {
    return new CommandObject<>(commandArguments(OBJECT).add(FREQ).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> objectRefcount(byte[] key) {
    return new CommandObject<>(commandArguments(OBJECT).add(REFCOUNT).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<byte[]> objectEncoding(byte[] key) {
    return new CommandObject<>(commandArguments(OBJECT).add(ENCODING).key(key), BuilderFactory.BINARY);
  }

  public final CommandObject<Long> objectIdletime(byte[] key) {
    return new CommandObject<>(commandArguments(OBJECT).add(IDLETIME).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> objectFreq(byte[] key) {
    return new CommandObject<>(commandArguments(OBJECT).add(FREQ).key(key), BuilderFactory.LONG);
  }

  public CommandObject<Long> waitReplicas(int replicas, long timeout) {
    return new CommandObject<>(commandArguments(WAIT).add(replicas).add(timeout), BuilderFactory.LONG);
  }

  public final CommandObject<Long> waitReplicas(String sampleKey, int replicas, long timeout) {
    return new CommandObject<>(commandArguments(WAIT).add(replicas).add(timeout).processKey(sampleKey), BuilderFactory.LONG);
  }

  public final CommandObject<Long> waitReplicas(byte[] sampleKey, int replicas, long timeout) {
    return new CommandObject<>(commandArguments(WAIT).add(replicas).add(timeout).processKey(sampleKey), BuilderFactory.LONG);
  }

  public final CommandObject<String> migrate(String host, int port, String key, int timeout) {
    return new CommandObject<>(commandArguments(MIGRATE).add(host).add(port).key(key).add(0).add(timeout), BuilderFactory.STRING);
  }

  public final CommandObject<String> migrate(String host, int port, int timeout, MigrateParams params, String... keys) {
    return new CommandObject<>(commandArguments(MIGRATE).add(host).add(port).add(new byte[0]).add(0)
        .add(timeout).addParams(params).add(Keyword.KEYS).keys((Object[]) keys), BuilderFactory.STRING);
  }

  public final CommandObject<String> migrate(String host, int port, byte[] key, int timeout) {
    return new CommandObject<>(commandArguments(MIGRATE).add(host).add(port).key(key).add(0).add(timeout), BuilderFactory.STRING);
  }

  public final CommandObject<String> migrate(String host, int port, int timeout, MigrateParams params, byte[]... keys) {
    return new CommandObject<>(commandArguments(MIGRATE).add(host).add(port).add(new byte[0]).add(0)
        .add(timeout).addParams(params).add(Keyword.KEYS).keys((Object[]) keys), BuilderFactory.STRING);
  }

  public final CommandObject<Long> publish(String channel, String message) {
    return new CommandObject<>(commandArguments(PUBLISH).add(channel).add(message), BuilderFactory.LONG);
  }

  public final CommandObject<Long> publish(byte[] channel, byte[] message) {
    return new CommandObject<>(commandArguments(PUBLISH).add(channel).add(message), BuilderFactory.LONG);
  }
  // Miscellaneous commands

  // RediSearch commands
  public CommandObject<String> ftCreate(String indexName, IndexOptions indexOptions, Schema schema) {
    CommandArguments args = commandArguments(SearchCommand.CREATE).add(indexName)
        .addParams(indexOptions).add(SearchKeyword.SCHEMA);
    schema.fields.forEach(field -> field.addParams(args));
    return new CommandObject<>(args, BuilderFactory.STRING);
  }

  public CommandObject<String> ftAlter(String indexName, Schema schema) {
    CommandArguments args = commandArguments(SearchCommand.ALTER).add(indexName)
        .add(SearchKeyword.SCHEMA).add(SearchKeyword.ADD);
    schema.fields.forEach(field -> field.addParams(args));
    return new CommandObject<>(args, BuilderFactory.STRING);
  }

  public CommandObject<SearchResult> ftSearch(String indexName, Query query) {
    return new CommandObject<>(commandArguments(SearchCommand.SEARCH).add(indexName).addParams(query),
        new SearchResultBuilder(!query.getNoContent(), query.getWithScores(), query.getWithPayloads(), true));
  }

  public CommandObject<SearchResult> ftSearch(byte[] indexName, Query query) {
    return new CommandObject<>(commandArguments(SearchCommand.SEARCH).add(indexName).addParams(query),
        new SearchResultBuilder(!query.getNoContent(), query.getWithScores(), query.getWithPayloads(), false));
  }

  public CommandObject<String> ftExplain(String indexName, Query query) {
    return new CommandObject<>(commandArguments(SearchCommand.EXPLAIN).add(indexName).addParams(query), BuilderFactory.STRING);
  }

  public CommandObject<List<String>> ftExplainCLI(String indexName, Query query) {
    return new CommandObject<>(commandArguments(SearchCommand.EXPLAINCLI).add(indexName).addParams(query), BuilderFactory.STRING_LIST);
  }

  public CommandObject<AggregationResult> ftAggregate(String indexName, AggregationBuilder aggr) {
    return new CommandObject<>(commandArguments(SearchCommand.AGGREGATE).add(indexName).addObjects(aggr.getArgs()),
        !aggr.isWithCursor() ? BuilderFactory.SEARCH_AGGREGATION_RESULT : BuilderFactory.SEARCH_AGGREGATION_RESULT_WITH_CURSOR);
  }

  public CommandObject<AggregationResult> ftCursorRead(String indexName, long cursorId, int count) {
    return new CommandObject<>(commandArguments(SearchCommand.CURSOR).add(SearchKeyword.READ)
        .add(indexName).add(cursorId).add(count), BuilderFactory.SEARCH_AGGREGATION_RESULT_WITH_CURSOR);
  }

  public CommandObject<String> ftCursorDel(String indexName, long cursorId) {
    return new CommandObject<>(commandArguments(SearchCommand.CURSOR).add(SearchKeyword.DEL)
        .add(indexName).add(cursorId), BuilderFactory.STRING);
  }

  public CommandObject<String> ftDropIndex(String indexName) {
    return new CommandObject<>(commandArguments(SearchCommand.DROPINDEX).add(indexName), BuilderFactory.STRING);
  }

  public CommandObject<String> ftDropIndexDD(String indexName) {
    return new CommandObject<>(commandArguments(SearchCommand.DROPINDEX).add(indexName).add(SearchKeyword.DD), BuilderFactory.STRING);
  }

  public CommandObject<String> ftSynUpdate(String indexName, String synonymGroupId, String... terms) {
    return new CommandObject<>(commandArguments(SearchCommand.SYNUPDATE).add(indexName)
        .add(synonymGroupId).addObjects((Object[]) terms), BuilderFactory.STRING);
  }

  public CommandObject<Map<String, List<String>>> ftSynDump(String indexName) {
    return new CommandObject<>(commandArguments(SearchCommand.SYNDUMP).add(indexName), BuilderFactory.SEARCH_SYNONYM_GROUPS);
  }

  public CommandObject<Map<String, Object>> ftInfo(String indexName) {
    return new CommandObject<>(commandArguments(SearchCommand.INFO).add(indexName), BuilderFactory.ENCODED_OBJECT_MAP);
  }

  public CommandObject<String> ftAliasAdd(String aliasName, String indexName) {
    return new CommandObject<>(commandArguments(SearchCommand.ALIASADD).add(aliasName).add(indexName), BuilderFactory.STRING);
  }

  public CommandObject<String> ftAliasUpdate(String aliasName, String indexName) {
    return new CommandObject<>(commandArguments(SearchCommand.ALIASUPDATE).add(aliasName).add(indexName), BuilderFactory.STRING);
  }

  public CommandObject<String> ftAliasDel(String aliasName) {
    return new CommandObject<>(commandArguments(SearchCommand.ALIASDEL).add(aliasName), BuilderFactory.STRING);
  }

  public final CommandObject<Map<String, String>> ftConfigGet(String option) {
    return new CommandObject<>(commandArguments(SearchCommand.CONFIG).add(SearchKeyword.GET).add(option), BuilderFactory.STRING_MAP_FROM_PAIRS);
  }

  public CommandObject<Map<String, String>> ftConfigGet(String indexName, String option) {
    return ftConfigGet(option);
  }

  public final CommandObject<String> ftConfigSet(String option, String value) {
    return new CommandObject<>(commandArguments(SearchCommand.CONFIG).add(SearchKeyword.SET).add(option).add(value), BuilderFactory.STRING);
  }

  public CommandObject<String> ftConfigSet(String indexName, String option, String value) {
    return ftConfigSet(option, value);
  }
  // RediSearch commands

  // RedisJSON commands
  public final CommandObject<String> jsonSet(String key, Path2 path, Object object) {
    return new CommandObject<>(commandArguments(JsonCommand.SET).key(key).add(path).add(object), BuilderFactory.STRING);
  }

  public final CommandObject<String> jsonSetWithEscape(String key, Path2 path, Object object) {
    return new CommandObject<>(commandArguments(JsonCommand.SET).key(key).add(path).add(GSON.toJson(object)), BuilderFactory.STRING);
  }

  public final CommandObject<String> jsonSet(String key, Path path, Object pojo) {
    return new CommandObject<>(commandArguments(JsonCommand.SET).key(key).add(path).add(GSON.toJson(pojo)), BuilderFactory.STRING);
  }

  public final CommandObject<String> jsonSet(String key, Path2 path, Object object, JsonSetParams params) {
    return new CommandObject<>(commandArguments(JsonCommand.SET).key(key).add(path).add(object).addParams(params), BuilderFactory.STRING);
  }

  public final CommandObject<String> jsonSetWithEscape(String key, Path2 path, Object object, JsonSetParams params) {
    return new CommandObject<>(commandArguments(JsonCommand.SET).key(key).add(path).add(GSON.toJson(object)).addParams(params), BuilderFactory.STRING);
  }

  public final CommandObject<String> jsonSet(String key, Path path, Object pojo, JsonSetParams params) {
    return new CommandObject<>(commandArguments(JsonCommand.SET).key(key).add(path).add(GSON.toJson(pojo)).addParams(params), BuilderFactory.STRING);
  }

  public final CommandObject<Object> jsonGet(String key) {
    return new CommandObject<>(commandArguments(JsonCommand.GET).key(key), new GsonObjectBuilder<>(Object.class));
  }

  public final <T> CommandObject<T> jsonGet(String key, Class<T> clazz) {
    return new CommandObject<>(commandArguments(JsonCommand.GET).key(key), new GsonObjectBuilder<>(clazz));
  }

  public final CommandObject<Object> jsonGet(String key, Path2... paths) {
    return new CommandObject<>(commandArguments(JsonCommand.GET).key(key).addObjects((Object[]) paths), BuilderFactory.JSON_OBJECT);
  }

  public final CommandObject<Object> jsonGet(String key, Path... paths) {
    return new CommandObject<>(commandArguments(JsonCommand.GET).key(key).addObjects((Object[]) paths), new GsonObjectBuilder<>(Object.class));
  }

  public final <T> CommandObject<T> jsonGet(String key, Class<T> clazz, Path... paths) {
    return new CommandObject<>(commandArguments(JsonCommand.GET).key(key).addObjects((Object[]) paths), new GsonObjectBuilder<>(clazz));
  }

  public final CommandObject<List<JSONArray>> jsonMGet(Path2 path, String... keys) {
    return new CommandObject<>(commandArguments(JsonCommand.MGET).keys((Object[]) keys).add(path), BuilderFactory.JSON_ARRAY_LIST);
  }

  public final <T> CommandObject<List<T>> jsonMGet(Path path, Class<T> clazz, String... keys) {
    return new CommandObject<>(commandArguments(JsonCommand.MGET).keys((Object[]) keys).add(path), new GsonObjectListBuilder<>(clazz));
  }

  public final CommandObject<Long> jsonDel(String key) {
    return new CommandObject<>(commandArguments(JsonCommand.DEL).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> jsonDel(String key, Path2 path) {
    return new CommandObject<>(commandArguments(JsonCommand.DEL).key(key).add(path), BuilderFactory.LONG);
  }

  public final CommandObject<Long> jsonDel(String key, Path path) {
    return new CommandObject<>(commandArguments(JsonCommand.DEL).key(key).add(path), BuilderFactory.LONG);
  }

  public final CommandObject<Long> jsonClear(String key) {
    return new CommandObject<>(commandArguments(JsonCommand.CLEAR).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<Long> jsonClear(String key, Path2 path) {
    return new CommandObject<>(commandArguments(JsonCommand.CLEAR).key(key).add(path), BuilderFactory.LONG);
  }

  public final CommandObject<Long> jsonClear(String key, Path path) {
    return new CommandObject<>(commandArguments(JsonCommand.CLEAR).key(key).add(path), BuilderFactory.LONG);
  }

  public final CommandObject<List<Boolean>> jsonToggle(String key, Path2 path) {
    return new CommandObject<>(commandArguments(JsonCommand.TOGGLE).key(key).add(path), BuilderFactory.BOOLEAN_LIST);
  }

  public final CommandObject<String> jsonToggle(String key, Path path) {
    return new CommandObject<>(commandArguments(JsonCommand.TOGGLE).key(key).add(path), BuilderFactory.STRING);
  }

  public final CommandObject<Class<?>> jsonType(String key) {
    return new CommandObject<>(commandArguments(JsonCommand.TYPE).key(key), BuilderFactory.JSON_TYPE);
  }

  public final CommandObject<List<Class<?>>> jsonType(String key, Path2 path) {
    return new CommandObject<>(commandArguments(JsonCommand.TYPE).key(key).add(path), BuilderFactory.JSON_TYPE_LIST);
  }

  public final CommandObject<Class<?>> jsonType(String key, Path path) {
    return new CommandObject<>(commandArguments(JsonCommand.TYPE).key(key).add(path), BuilderFactory.JSON_TYPE);
  }

  public final CommandObject<Long> jsonStrAppend(String key, Object string) {
    return new CommandObject<>(commandArguments(JsonCommand.STRAPPEND).key(key).add(GSON.toJson(string)), BuilderFactory.LONG);
  }

  public final CommandObject<List<Long>> jsonStrAppend(String key, Path2 path, Object string) {
    return new CommandObject<>(commandArguments(JsonCommand.STRAPPEND).key(key).add(path).add(GSON.toJson(string)), BuilderFactory.LONG_LIST);
  }

  public final CommandObject<Long> jsonStrAppend(String key, Path path, Object string) {
    return new CommandObject<>(commandArguments(JsonCommand.STRAPPEND).key(key).add(path).add(GSON.toJson(string)), BuilderFactory.LONG);
  }

  public final CommandObject<Long> jsonStrLen(String key) {
    return new CommandObject<>(commandArguments(JsonCommand.STRLEN).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<List<Long>> jsonStrLen(String key, Path2 path) {
    return new CommandObject<>(commandArguments(JsonCommand.STRLEN).key(key).add(path), BuilderFactory.LONG_LIST);
  }

  public final CommandObject<Long> jsonStrLen(String key, Path path) {
    return new CommandObject<>(commandArguments(JsonCommand.STRLEN).key(key).add(path), BuilderFactory.LONG);
  }

  public final CommandObject<Long> jsonArrAppend(String key, String path, JSONObject... objects) {
    CommandArguments args = commandArguments(JsonCommand.ARRAPPEND).key(key).add(path);
    for (Object object : objects) {
      args.add(object);
    }
    return new CommandObject<>(args, BuilderFactory.LONG);
  }

  public final CommandObject<List<Long>> jsonArrAppend(String key, Path2 path, Object... objects) {
    CommandArguments args = commandArguments(JsonCommand.ARRAPPEND).key(key).add(path).addObjects(objects);
    return new CommandObject<>(args, BuilderFactory.LONG_LIST);
  }

  public final CommandObject<List<Long>> jsonArrAppendWithEscape(String key, Path2 path, Object... objects) {
    CommandArguments args = commandArguments(JsonCommand.ARRAPPEND).key(key).add(path);
    for (Object object : objects) {
      args.add(GSON.toJson(object));
    }
    return new CommandObject<>(args, BuilderFactory.LONG_LIST);
  }

  public final CommandObject<Long> jsonArrAppend(String key, Path path, Object... pojos) {
    CommandArguments args = commandArguments(JsonCommand.ARRAPPEND).key(key).add(path);
    for (Object pojo : pojos) {
      args.add(GSON.toJson(pojo));
    }
    return new CommandObject<>(args, BuilderFactory.LONG);
  }

  public final CommandObject<List<Long>> jsonArrIndex(String key, Path2 path, Object scalar) {
    return new CommandObject<>(commandArguments(JsonCommand.ARRINDEX).key(key).add(path).add(scalar), BuilderFactory.LONG_LIST);
  }

  public final CommandObject<List<Long>> jsonArrIndexWithEscape(String key, Path2 path, Object scalar) {
    return new CommandObject<>(commandArguments(JsonCommand.ARRINDEX).key(key).add(path).add(GSON.toJson(scalar)), BuilderFactory.LONG_LIST);
  }

  public final CommandObject<Long> jsonArrIndex(String key, Path path, Object scalar) {
    return new CommandObject<>(commandArguments(JsonCommand.ARRINDEX).key(key).add(path).add(GSON.toJson(scalar)), BuilderFactory.LONG);
  }

  public final CommandObject<List<Long>> jsonArrInsert(String key, Path2 path, int index, Object... objects) {
    CommandArguments args = commandArguments(JsonCommand.ARRINSERT).key(key).add(path).add(index).addObjects(objects);
    return new CommandObject<>(args, BuilderFactory.LONG_LIST);
  }

  public final CommandObject<List<Long>> jsonArrInsertWithEscape(String key, Path2 path, int index, Object... objects) {
    CommandArguments args = commandArguments(JsonCommand.ARRINSERT).key(key).add(path).add(index);
    for (Object object : objects) {
      args.add(GSON.toJson(object));
    }
    return new CommandObject<>(args, BuilderFactory.LONG_LIST);
  }

  public final CommandObject<Long> jsonArrInsert(String key, Path path, int index, Object... pojos) {
    CommandArguments args = commandArguments(JsonCommand.ARRINSERT).key(key).add(path).add(index);
    for (Object pojo : pojos) {
      args.add(GSON.toJson(pojo));
    }
    return new CommandObject<>(args, BuilderFactory.LONG);
  }

  public final CommandObject<Object> jsonArrPop(String key) {
    return new CommandObject<>(commandArguments(JsonCommand.ARRPOP).key(key), new GsonObjectBuilder<>(Object.class));
  }

  public final <T> CommandObject<T> jsonArrPop(String key, Class<T> clazz) {
    return new CommandObject<>(commandArguments(JsonCommand.ARRPOP).key(key), new GsonObjectBuilder<>(clazz));
  }

  public final CommandObject<List<Object>> jsonArrPop(String key, Path2 path) {
    return new CommandObject<>(commandArguments(JsonCommand.ARRPOP).key(key).add(path), new GsonObjectListBuilder<>(Object.class));
  }

  public final CommandObject<Object> jsonArrPop(String key, Path path) {
    return new CommandObject<>(commandArguments(JsonCommand.ARRPOP).key(key).add(path), new GsonObjectBuilder<>(Object.class));
  }

  public final <T> CommandObject<T> jsonArrPop(String key, Class<T> clazz, Path path) {
    return new CommandObject<>(commandArguments(JsonCommand.ARRPOP).key(key).add(path), new GsonObjectBuilder<>(clazz));
  }

  public final CommandObject<List<Object>> jsonArrPop(String key, Path2 path, int index) {
    return new CommandObject<>(commandArguments(JsonCommand.ARRPOP).key(key).add(path).add(index), new GsonObjectListBuilder<>(Object.class));
  }

  public final CommandObject<Object> jsonArrPop(String key, Path path, int index) {
    return new CommandObject<>(commandArguments(JsonCommand.ARRPOP).key(key).add(path).add(index), new GsonObjectBuilder<>(Object.class));
  }

  public final <T> CommandObject<T> jsonArrPop(String key, Class<T> clazz, Path path, int index) {
    return new CommandObject<>(commandArguments(JsonCommand.ARRPOP).key(key).add(path).add(index), new GsonObjectBuilder<>(clazz));
  }

  public final CommandObject<Long> jsonArrLen(String key) {
    return new CommandObject<>(commandArguments(JsonCommand.ARRLEN).key(key), BuilderFactory.LONG);
  }

  public final CommandObject<List<Long>> jsonArrLen(String key, Path2 path) {
    return new CommandObject<>(commandArguments(JsonCommand.ARRLEN).key(key).add(path), BuilderFactory.LONG_LIST);
  }

  public final CommandObject<Long> jsonArrLen(String key, Path path) {
    return new CommandObject<>(commandArguments(JsonCommand.ARRLEN).key(key).add(path), BuilderFactory.LONG);
  }

  public final CommandObject<List<Long>> jsonArrTrim(String key, Path2 path, int start, int stop) {
    return new CommandObject<>(commandArguments(JsonCommand.ARRTRIM).key(key).add(path).add(start).add(stop), BuilderFactory.LONG_LIST);
  }

  public final CommandObject<Long> jsonArrTrim(String key, Path path, int start, int stop) {
    return new CommandObject<>(commandArguments(JsonCommand.ARRTRIM).key(key).add(path).add(start).add(stop), BuilderFactory.LONG);
  }
  // RedisJSON commands

  private static final Gson GSON = new Gson();

  private class GsonObjectBuilder<T> extends Builder<T> {

    private final Class<T> clazz;

    public GsonObjectBuilder(Class<T> clazz) {
      this.clazz = clazz;
    }

    @Override
    public T build(Object data) {
      return GSON.fromJson(BuilderFactory.STRING.build(data), clazz);
    }
  }

  private class GsonObjectListBuilder<T> extends Builder<List<T>> {

    private final Class<T> clazz;

    public GsonObjectListBuilder(Class<T> clazz) {
      this.clazz = clazz;
    }

    @Override
    public List<T> build(Object data) {
      if (data == null) {
        return null;
      }
      List<String> list = BuilderFactory.STRING_LIST.build(data);
      return list.stream().map(s -> GSON.fromJson(s, clazz)).collect(Collectors.toList());
    }
  }

  private CommandArguments addFlatKeyValueArgs(CommandArguments args, String... keyvalues) {
    for (int i = 0; i < keyvalues.length; i += 2) {
      args.key(keyvalues[i]).add(keyvalues[i + 1]);
    }
    return args;
  }

  private CommandArguments addFlatKeyValueArgs(CommandArguments args, byte[]... keyvalues) {
    for (int i = 0; i < keyvalues.length; i += 2) {
      args.key(keyvalues[i]).add(keyvalues[i + 1]);
    }
    return args;
  }

  private CommandArguments addFlatMapArgs(CommandArguments args, Map<?, ?> map) {
    for (Map.Entry<? extends Object, ? extends Object> entry : map.entrySet()) {
      args.add(entry.getKey());
      args.add(entry.getValue());
    }
    return args;
  }

  private CommandArguments addSortedSetFlatMapArgs(CommandArguments args, Map<?, Double> map) {
    for (Map.Entry<? extends Object, Double> entry : map.entrySet()) {
      args.add(entry.getValue());
      args.add(entry.getKey());
    }
    return args;
  }

  private CommandArguments addGeoCoordinateFlatMapArgs(CommandArguments args, Map<?, GeoCoordinate> map) {
    for (Map.Entry<? extends Object, GeoCoordinate> entry : map.entrySet()) {
      GeoCoordinate ord = entry.getValue();
      args.add(ord.getLongitude());
      args.add(ord.getLatitude());
      args.add(entry.getKey());
    }
    return args;
  }
}
