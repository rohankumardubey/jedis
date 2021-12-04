package redis.clients.jedis.commands;

import java.util.List;
import java.util.Map;

import redis.clients.jedis.Response;
import redis.clients.jedis.params.*;
import redis.clients.jedis.stream.*;

public interface StreamPipelineCommands {

  /**
   * XADD key ID field string [field string ...]
   *
   * @param key
   * @param id
   * @param hash
   * @return the ID of the added entry
   */
  Response<StreamEntryID> xadd(String key, StreamEntryID id, Map<String, String> hash);

  /**
   * XADD key [NOMKSTREAM] [MAXLEN|MINID [=|~] threshold [LIMIT count]] *|ID field value [field value ...]
   *
   * @param key
   * @param hash
   * @param params
   * @return the ID of the added entry
   */
  default Response<StreamEntryID> xadd(String key, Map<String, String> hash, XAddParams params) {
    return xadd_v2(key, params, hash);
  }

  Response<StreamEntryID> xadd_v2(String key, XAddParams params, Map<String, String> hash);

  /**
   * XLEN key
   *
   * @param key
   * @return length of stream
   */
  Response<Long> xlen(String key);

  /**
   * XRANGE key start end
   *
   * @param key
   * @param start minimum {@link StreamEntryID} for the retrieved range, passing <code>null</code> will indicate minimum ID possible in the stream
   * @param end maximum {@link StreamEntryID} for the retrieved range, passing <code>null</code> will indicate maximum ID possible in the stream
   * @return The entries with IDs matching the specified range.
   */
  Response<List<StreamEntry>> xrange(String key, StreamEntryID start, StreamEntryID end);

  /**
   * XRANGE key start end COUNT count
   *
   * @param key
   * @param start minimum {@link StreamEntryID} for the retrieved range, passing <code>null</code> will indicate minimum ID possible in the stream
   * @param end maximum {@link StreamEntryID} for the retrieved range, passing <code>null</code> will indicate maximum ID possible in the stream
   * @param count maximum number of entries returned
   * @return The entries with IDs matching the specified range.
   */
  Response<List<StreamEntry>> xrange(String key, StreamEntryID start, StreamEntryID end, int count);

  /**
   * XREVRANGE key end start
   *
   * @param key
   * @param start minimum {@link StreamEntryID} for the retrieved range, passing <code>null</code> will indicate minimum ID possible in the stream
   * @param end maximum {@link StreamEntryID} for the retrieved range, passing <code>null</code> will indicate maximum ID possible in the stream
   * @return the entries with IDs matching the specified range, from the higher ID to the lower ID matching.
   */
  Response<List<StreamEntry>> xrevrange(String key, StreamEntryID end, StreamEntryID start);

  /**
   * XREVRANGE key end start COUNT count
   *
   * @param key
   * @param start minimum {@link StreamEntryID} for the retrieved range, passing <code>null</code> will indicate minimum ID possible in the stream
   * @param end maximum {@link StreamEntryID} for the retrieved range, passing <code>null</code> will indicate maximum ID possible in the stream
   * @param count The entries with IDs matching the specified range.
   * @return the entries with IDs matching the specified range, from the higher ID to the lower ID matching.
   */
  Response<List<StreamEntry>> xrevrange(String key, StreamEntryID end, StreamEntryID start, int count);

  /**
   * XACK key group ID [ID ...]
   *
   * @param key
   * @param group
   * @param ids
   */
  Response<Long> xack(String key, String group, StreamEntryID... ids);

  /**
   * XGROUP CREATE <key> <groupname> <id or $>
   *
   * @param key
   * @param groupname
   * @param id
   * @param makeStream
   */
  Response<String> xgroupCreate( String key, String groupname, StreamEntryID id, boolean makeStream);

  /**
   * XGROUP SETID <key> <groupname> <id or $>
   *
   * @param key
   * @param groupname
   * @param id
   */
  Response<String> xgroupSetID( String key, String groupname, StreamEntryID id);

  /**
   * XGROUP DESTROY <key> <groupname>
   *
   * @param key
   * @param groupname
   */
  Response<Long> xgroupDestroy(String key, String groupname);

  /**
   * XGROUP DELCONSUMER <key> <groupname> <consumername>
   * @param key
   * @param groupname
   * @param consumername
   */
  Response<Long> xgroupDelConsumer( String key, String groupname, String consumername);

  /**
   * XPENDING key group
   *
   * @param key
   * @param groupname
   */
  Response<StreamPendingSummary> xpending(String key, String groupname);

  /**
   * XPENDING key group [start end count] [consumer]
   *
   * @param key
   * @param groupname
   * @param start
   * @param end
   * @param count
   * @param consumername
   */
  Response<List<StreamPendingEntry>> xpending(String key, String groupname, StreamEntryID start,
      StreamEntryID end, int count, String consumername);

  /**
   * XPENDING key group [[IDLE min-idle-time] start end count [consumer]]
   *
   * @param key
   * @param groupname
   * @param params
   */
  Response<List<StreamPendingEntry>> xpending(String key, String groupname, XPendingParams params);

  /**
   * XDEL key ID [ID ...]
   * @param key
   * @param ids
   */
  Response<Long> xdel(String key, StreamEntryID... ids);

  /**
   * XTRIM key MAXLEN [~] count
   * @param key
   * @param maxLen
   * @param approximate
   */
  Response<Long> xtrim(String key, long maxLen, boolean approximate);

  /**
   * XTRIM key MAXLEN|MINID [=|~] threshold [LIMIT count]
   * @param key
   * @param params
   */
  Response<Long> xtrim(String key, XTrimParams params);

  /**
   *  XCLAIM <key> <group> <consumer> <min-idle-time> <ID-1> ... <ID-N>
   *        [IDLE <milliseconds>] [TIME <mstime>] [RETRYCOUNT <count>]
   *        [FORCE]
   */
  Response<List<StreamEntry>> xclaim(String key, String group, String consumername, long minIdleTime,
      XClaimParams params, StreamEntryID... ids);

  /**
   *  XCLAIM <key> <group> <consumer> <min-idle-time> <ID-1> ... <ID-N>
   *        [IDLE <milliseconds>] [TIME <mstime>] [RETRYCOUNT <count>]
   *        [FORCE] JUSTID
   */
  Response<List<StreamEntryID>> xclaimJustId(String key, String group, String consumername, long minIdleTime,
      XClaimParams params, StreamEntryID... ids);

  /**
   * XAUTOCLAIM key group consumer min-idle-time start [COUNT count]
   *
   * @param key Stream Key
   * @param group Consumer Group
   * @param consumerName Consumer name to transfer the auto claimed entries
   * @param minIdleTime Entries pending more than minIdleTime will be transferred ownership
   * @param start {@link StreamEntryID} - Entries >= start will be transferred ownership, passing <code>null</code> will indicate '-'
   * @param params {@link XAutoClaimParams}
   */
  Response<Map.Entry<StreamEntryID, List<StreamEntry>>> xautoclaim(String key, String group, String consumerName,
      long minIdleTime, StreamEntryID start, XAutoClaimParams params);

  /**
   * XAUTOCLAIM key group consumer min-idle-time start [COUNT count] JUSTID
   *
   * @param key Stream Key
   * @param group Consumer Group
   * @param consumerName Consumer name to transfer the auto claimed entries
   * @param minIdleTime Entries pending more than minIdleTime will be transferred ownership
   * @param start {@link StreamEntryID} - Entries >= start will be transferred ownership, passing <code>null</code> will indicate '-'
   * @param params {@link XAutoClaimParams}
   */
  Response<Map.Entry<StreamEntryID, List<StreamEntryID>>> xautoclaimJustId(String key, String group, String consumerName,
      long minIdleTime, StreamEntryID start, XAutoClaimParams params);

  /**
   * Introspection command used in order to retrieve different information about the stream
   * @param key Stream name
   * @return {@link StreamInfo} that contains information about the stream
   */
  Response<StreamInfo> xinfoStream (String key);

  /**
   * Introspection command used in order to retrieve different information about groups in the stream
   * @param key Stream name
   * @return List of {@link StreamGroupInfo} containing information about groups
   */
  Response<List<StreamGroupInfo>> xinfoGroup (String key);

  /**
   * Introspection command used in order to retrieve different information about consumers in the group
   * @param key Stream name
   * @param group Group name
   * @return List of {@link StreamConsumersInfo} containing information about consumers that belong
   * to the the group
   */
  Response<List<StreamConsumersInfo>> xinfoConsumers (String key, String group);

  /**
   * XREAD [COUNT count] [BLOCK milliseconds] STREAMS key [key ...] ID [ID ...]
   *
   * @param xReadParams
   * @param streams
   */
  Response<List<Map.Entry<String, List<StreamEntry>>>> xread(XReadParams xReadParams,
      Map<String, StreamEntryID> streams);

  /**
   * @deprecated This method will be removed due to bug regarding {@code block} param. Use
   * {@link StreamPipelineCommands#xreadGroup(String, String, XReadGroupParams, Map)}.
   */
  @Deprecated
  default Response<List<Map.Entry<String, List<StreamEntry>>>> xreadGroup(final String groupname,
      final String consumer, final int count, final long block, final boolean noAck,
      final Map.Entry<String, StreamEntryID>... streams) {
    if (block > Integer.MAX_VALUE) throw new IllegalArgumentException();
    XReadGroupParams params = XReadGroupParams.xReadGroupParams();
    if (count > 0) params.count(count);
    if (block > 0) params.block((int) block);
    if (noAck) params.noAck();
    Map<String, StreamEntryID> streamMap = new java.util.LinkedHashMap<>(streams.length);
    for (Map.Entry<String, StreamEntryID> stream : streams) {
      streamMap.put(stream.getKey(), stream.getValue());
    }
    return xreadGroup(groupname, consumer, params, streamMap);
  }

  /**
   * XREAD [COUNT count] [BLOCK milliseconds] STREAMS key [key ...] ID [ID ...]
   *
   * @param groupname
   * @param consumer
   * @param xReadGroupParams
   * @param streams
   */
  Response<List<Map.Entry<String, List<StreamEntry>>>> xreadGroup(String groupname, String consumer,
      XReadGroupParams xReadGroupParams, Map<String, StreamEntryID> streams);

}
