package redis.clients.jedis;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import redis.clients.jedis.exceptions.JedisException;

import redis.clients.jedis.resps.LCSMatchResult.MatchedPosition;
import redis.clients.jedis.resps.LCSMatchResult.Position;
import redis.clients.jedis.resps.*;
import redis.clients.jedis.search.aggr.AggregationResult;
import redis.clients.jedis.stream.*;
import redis.clients.jedis.util.JedisByteHashMap;
import redis.clients.jedis.util.SafeEncoder;

public final class BuilderFactory {

  public static final Builder<Object> RAW_OBJECT = new Builder<Object>() {
    @Override
    public Object build(Object data) {
      return data;
    }

    @Override
    public String toString() {
      return "Object";
    }
  };

  public static final Builder<List<Object>> RAW_OBJECT_LIST = new Builder<List<Object>>() {
    @Override
    public List<Object> build(Object data) {
      return (List<Object>) data;
    }

    @Override
    public String toString() {
      return "List<Object>";
    }
  };

  public static final Builder<Object> ENCODED_OBJECT = new Builder<Object>() {
    @Override
    public Object build(Object data) {
      return SafeEncoder.encodeObject(data);
    }

    @Override
    public String toString() {
      return "Object";
    }
  };

  public static final Builder<List<Object>> ENCODED_OBJECT_LIST = new Builder<List<Object>>() {
    @Override
    public List<Object> build(Object data) {
      return (List<Object>) SafeEncoder.encodeObject(data);
    }

    @Override
    public String toString() {
      return "List<Object>";
    }
  };

  public static final Builder<Map<String, Object>> ENCODED_OBJECT_MAP = new Builder<Map<String, Object>>() {
    @Override
    public Map<String, Object> build(Object data) {
      final List list = (List) data;
      final Map<String, Object> map = new HashMap<>(list.size() / 2, 1);
      final Iterator iterator = list.iterator();
      while (iterator.hasNext()) {
        map.put(STRING.build(iterator.next()), ENCODED_OBJECT.build(iterator.next()));
      }
      return map;
    }
  };

  public static final Builder<Long> LONG = new Builder<Long>() {
    @Override
    public Long build(Object data) {
      return (Long) data;
    }

    @Override
    public String toString() {
      return "Long";
    }

  };

  public static final Builder<List<Long>> LONG_LIST = new Builder<List<Long>>() {
    @Override
    @SuppressWarnings("unchecked")
    public List<Long> build(Object data) {
      if (null == data) {
        return null;
      }
      return (List<Long>) data;
    }

    @Override
    public String toString() {
      return "List<Long>";
    }

  };

  public static final Builder<Double> DOUBLE = new Builder<Double>() {
    @Override
    public Double build(Object data) {
      String string = STRING.build(data);
      if (string == null) return null;
      try {
        return Double.valueOf(string);
      } catch (NumberFormatException e) {
        if (string.equals("inf") || string.equals("+inf")) return Double.POSITIVE_INFINITY;
        if (string.equals("-inf")) return Double.NEGATIVE_INFINITY;
        throw e;
      }
    }

    @Override
    public String toString() {
      return "Double";
    }
  };

  public static final Builder<List<Double>> DOUBLE_LIST = new Builder<List<Double>>() {
    @Override
    @SuppressWarnings("unchecked")
    public List<Double> build(Object data) {
      if (null == data) {
        return null;
      }
      List<byte[]> values = (List<byte[]>) data;
      List<Double> doubles = new ArrayList<>(values.size());
      for (byte[] value : values) {
        doubles.add(DOUBLE.build(value));
      }
      return doubles;
    }

    @Override
    public String toString() {
      return "List<Double>";
    }
  };

  public static final Builder<Boolean> BOOLEAN = new Builder<Boolean>() {
    @Override
    public Boolean build(Object data) {
      if (data == null) return null;
      return ((Long) data) == 1L;
    }

    @Override
    public String toString() {
      return "Boolean";
    }
  };

  public static final Builder<List<Boolean>> BOOLEAN_LIST = new Builder<List<Boolean>>() {
    @Override
    @SuppressWarnings("unchecked")
    public List<Boolean> build(Object data) {
      if (null == data) {
        return null;
      }
      List<Long> longs = (List<Long>) data;
      List<Boolean> booleans = new ArrayList<>(longs.size());
      for (Long value : longs) {
        booleans.add(value == null ? null : value == 1L);
      }
      return booleans;
    }

    @Override
    public String toString() {
      return "List<Boolean>";
    }
  };

  public static final Builder<byte[]> BYTE_ARRAY = new Builder<byte[]>() {
    @Override
    public byte[] build(Object data) {
      return ((byte[]) data);
    }

    @Override
    public String toString() {
      return "byte[]";
    }
  };

  public static final Builder<List<byte[]>> BYTE_ARRAY_LIST = new Builder<List<byte[]>>() {
    @Override
    @SuppressWarnings("unchecked")
    public List<byte[]> build(Object data) {
      if (null == data) {
        return null;
      }
      return (List<byte[]>) data;
    }

    @Override
    public String toString() {
      return "List<byte[]>";
    }
  };

  public static final Builder<byte[]> BINARY = new Builder<byte[]>() {
    @Override
    public byte[] build(Object data) {
      return (byte[]) data;
    }

    @Override
    public String toString() {
      return "byte[]";
    }
  };

  public static final Builder<List<byte[]>> BINARY_LIST = new Builder<List<byte[]>>() {
    @Override
    @SuppressWarnings("unchecked")
    public List<byte[]> build(Object data) {
      return (List<byte[]>) data;
    }

    @Override
    public String toString() {
      return "List<byte[]>";
    }
  };

  public static final Builder<Set<byte[]>> BINARY_SET = new Builder<Set<byte[]>>() {
    @Override
    @SuppressWarnings("unchecked")
    public Set<byte[]> build(Object data) {
      if (null == data) {
        return null;
      }
      List<byte[]> l = BINARY_LIST.build(data);
      return SetFromList.of(l);
    }

    @Override
    public String toString() {
      return "Set<byte[]>";
    }
  };

  public static final Builder<Map<byte[], byte[]>> BINARY_MAP = new Builder<Map<byte[], byte[]>>() {
    @Override
    @SuppressWarnings("unchecked")
    public Map<byte[], byte[]> build(Object data) {
      final List<byte[]> flatHash = (List<byte[]>) data;
      final Map<byte[], byte[]> hash = new JedisByteHashMap();
      final Iterator<byte[]> iterator = flatHash.iterator();
      while (iterator.hasNext()) {
        hash.put(iterator.next(), iterator.next());
      }

      return hash;
    }

    @Override
    public String toString() {
      return "Map<byte[], byte[]>";
    }
  };

  public static final Builder<String> STRING = new Builder<String>() {
    @Override
    public String build(Object data) {
      return data == null ? null : SafeEncoder.encode((byte[]) data);
    }

    @Override
    public String toString() {
      return "String";
    }

  };

  public static final Builder<List<String>> STRING_LIST = new Builder<List<String>>() {
    @Override
    @SuppressWarnings("unchecked")
    public List<String> build(Object data) {
      if (null == data) {
        return null;
      }
      List<byte[]> l = (List<byte[]>) data;
      final ArrayList<String> result = new ArrayList<>(l.size());
      for (final byte[] barray : l) {
        if (barray == null) {
          result.add(null);
        } else {
          result.add(SafeEncoder.encode(barray));
        }
      }
      return result;
    }

    @Override
    public String toString() {
      return "List<String>";
    }

  };

  public static final Builder<Set<String>> STRING_SET = new Builder<Set<String>>() {
    @Override
    @SuppressWarnings("unchecked")
    public Set<String> build(Object data) {
      if (null == data) {
        return null;
      }
      List<byte[]> l = (List<byte[]>) data;
      final Set<String> result = new HashSet<>(l.size(), 1);
      for (final byte[] barray : l) {
        if (barray == null) {
          result.add(null);
        } else {
          result.add(SafeEncoder.encode(barray));
        }
      }
      return result;
    }

    @Override
    public String toString() {
      return "Set<String>";
    }

  };

  public static final Builder<Map<String, String>> STRING_MAP = new Builder<Map<String, String>>() {
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, String> build(Object data) {
      final List<byte[]> flatHash = (List<byte[]>) data;
      final Map<String, String> hash = new HashMap<>(flatHash.size() / 2, 1);
      final Iterator<byte[]> iterator = flatHash.iterator();
      while (iterator.hasNext()) {
        hash.put(SafeEncoder.encode(iterator.next()), SafeEncoder.encode(iterator.next()));
      }

      return hash;
    }

    @Override
    public String toString() {
      return "Map<String, String>";
    }

  };

  public static final Builder<KeyedListElement> KEYED_LIST_ELEMENT = new Builder<KeyedListElement>() {
    @Override
    @SuppressWarnings("unchecked")
    public KeyedListElement build(Object data) {
      if (data == null) return null;
      List<byte[]> l = (List<byte[]>) data;
      return new KeyedListElement(l.get(0), l.get(1));
    }

    @Override
    public String toString() {
      return "KeyedListElement";
    }
  };

  public static final Builder<Tuple> TUPLE = new Builder<Tuple>() {
    @Override
    @SuppressWarnings("unchecked")
    public Tuple build(Object data) {
      List<byte[]> l = (List<byte[]>) data; // never null
      if (l.isEmpty()) {
        return null;
      }
      return new Tuple(l.get(0), DOUBLE.build(l.get(1)));
    }

    @Override
    public String toString() {
      return "Tuple";
    }

  };

  public static final Builder<KeyedZSetElement> KEYED_ZSET_ELEMENT = new Builder<KeyedZSetElement>() {
    @Override
    @SuppressWarnings("unchecked")
    public KeyedZSetElement build(Object data) {
      List<byte[]> l = (List<byte[]>) data; // never null
      if (l.isEmpty()) {
        return null;
      }
      return new KeyedZSetElement(l.get(0), l.get(1), DOUBLE.build(l.get(2)));
    }

    @Override
    public String toString() {
      return "KeyedZSetElement";
    }
  };

  public static final Builder<List<Tuple>> TUPLE_LIST = new Builder<List<Tuple>>() {
    @Override
    @SuppressWarnings("unchecked")
    public List<Tuple> build(Object data) {
      if (null == data) {
        return null;
      }
      List<byte[]> l = (List<byte[]>) data;
      final List<Tuple> result = new ArrayList<>(l.size() / 2);
      Iterator<byte[]> iterator = l.iterator();
      while (iterator.hasNext()) {
        result.add(new Tuple(iterator.next(), DOUBLE.build(iterator.next())));
      }
      return result;
    }

    @Override
    public String toString() {
      return "List<Tuple>";
    }
  };

  public static final Builder<Set<Tuple>> TUPLE_ZSET = new Builder<Set<Tuple>>() {
    @Override
    @SuppressWarnings("unchecked")
    public Set<Tuple> build(Object data) {
      if (null == data) {
        return null;
      }
      List<byte[]> l = (List<byte[]>) data;
      final Set<Tuple> result = new LinkedHashSet<>(l.size() / 2, 1);
      Iterator<byte[]> iterator = l.iterator();
      while (iterator.hasNext()) {
        result.add(new Tuple(iterator.next(), DOUBLE.build(iterator.next())));
      }
      return result;
    }

    @Override
    public String toString() {
      return "ZSet<Tuple>";
    }

  };

  public static final Builder<ScanResult<String>> SCAN_RESPONSE = new Builder<ScanResult<String>>() {
    @Override
    public ScanResult<String> build(Object data) {
      List<Object> result = (List<Object>) data;
      String newcursor = new String((byte[]) result.get(0));
      List<byte[]> rawResults = (List<byte[]>) result.get(1);
      List<String> results = new ArrayList<>(rawResults.size());
      for (byte[] bs : rawResults) {
        results.add(SafeEncoder.encode(bs));
      }
      return new ScanResult<>(newcursor, results);
    }
  };

  public static final Builder<ScanResult<Map.Entry<String, String>>> HSCAN_RESPONSE
      = new Builder<ScanResult<Map.Entry<String, String>>>() {
    @Override
    public ScanResult<Map.Entry<String, String>> build(Object data) {
      List<Object> result = (List<Object>) data;
      String newcursor = new String((byte[]) result.get(0));
      List<byte[]> rawResults = (List<byte[]>) result.get(1);
      List<Map.Entry<String, String>> results = new ArrayList<>(rawResults.size() / 2);
      Iterator<byte[]> iterator = rawResults.iterator();
      while (iterator.hasNext()) {
        results.add(new AbstractMap.SimpleEntry<>(SafeEncoder.encode(iterator.next()),
            SafeEncoder.encode(iterator.next())));
      }
      return new ScanResult<>(newcursor, results);
    }
  };

  public static final Builder<ScanResult<String>> SSCAN_RESPONSE = new Builder<ScanResult<String>>() {
    @Override
    public ScanResult<String> build(Object data) {
      List<Object> result = (List<Object>) data;
      String newcursor = new String((byte[]) result.get(0));
      List<byte[]> rawResults = (List<byte[]>) result.get(1);
      List<String> results = new ArrayList<>(rawResults.size());
      for (byte[] bs : rawResults) {
        results.add(SafeEncoder.encode(bs));
      }
      return new ScanResult<>(newcursor, results);
    }
  };

  public static final Builder<ScanResult<Tuple>> ZSCAN_RESPONSE = new Builder<ScanResult<Tuple>>() {
    @Override
    public ScanResult<Tuple> build(Object data) {
      List<Object> result = (List<Object>) data;
      String newcursor = new String((byte[]) result.get(0));
      List<byte[]> rawResults = (List<byte[]>) result.get(1);
      List<Tuple> results = new ArrayList<>(rawResults.size() / 2);
      Iterator<byte[]> iterator = rawResults.iterator();
      while (iterator.hasNext()) {
        results.add(new Tuple(iterator.next(), BuilderFactory.DOUBLE.build(iterator.next())));
      }
      return new ScanResult<>(newcursor, results);
    }
  };

  public static final Builder<ScanResult<byte[]>> SCAN_BINARY_RESPONSE = new Builder<ScanResult<byte[]>>() {
    @Override
    public ScanResult<byte[]> build(Object data) {
      List<Object> result = (List<Object>) data;
      byte[] newcursor = (byte[]) result.get(0);
      List<byte[]> rawResults = (List<byte[]>) result.get(1);
      return new ScanResult<>(newcursor, rawResults);
    }
  };

  public static final Builder<ScanResult<Map.Entry<byte[], byte[]>>> HSCAN_BINARY_RESPONSE
      = new Builder<ScanResult<Map.Entry<byte[], byte[]>>>() {
    @Override
    public ScanResult<Map.Entry<byte[], byte[]>> build(Object data) {
      List<Object> result = (List<Object>) data;
      byte[] newcursor = (byte[]) result.get(0);
      List<byte[]> rawResults = (List<byte[]>) result.get(1);
      List<Map.Entry<byte[], byte[]>> results = new ArrayList<>(rawResults.size() / 2);
      Iterator<byte[]> iterator = rawResults.iterator();
      while (iterator.hasNext()) {
        results.add(new AbstractMap.SimpleEntry<>(iterator.next(), iterator.next()));
      }
      return new ScanResult<>(newcursor, results);
    }
  };

  public static final Builder<ScanResult<byte[]>> SSCAN_BINARY_RESPONSE = new Builder<ScanResult<byte[]>>() {
    @Override
    public ScanResult<byte[]> build(Object data) {
      List<Object> result = (List<Object>) data;
      byte[] newcursor = (byte[]) result.get(0);
      List<byte[]> rawResults = (List<byte[]>) result.get(1);
      return new ScanResult<>(newcursor, rawResults);
    }
  };

  public static final Builder<Map<String, Long>> PUBSUB_NUMSUB_MAP = new Builder<Map<String, Long>>() {
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Long> build(Object data) {
      final List<Object> flatHash = (List<Object>) data;
      final Map<String, Long> hash = new HashMap<>(flatHash.size() / 2, 1);
      final Iterator<Object> iterator = flatHash.iterator();
      while (iterator.hasNext()) {
        hash.put(SafeEncoder.encode((byte[]) iterator.next()), (Long) iterator.next());
      }
      return hash;
    }

    @Override
    public String toString() {
      return "PUBSUB_NUMSUB_MAP<String, String>";
    }
  };

  public static final Builder<List<GeoCoordinate>> GEO_COORDINATE_LIST = new Builder<List<GeoCoordinate>>() {
    @Override
    public List<GeoCoordinate> build(Object data) {
      if (null == data) {
        return null;
      }
      return interpretGeoposResult((List<Object>) data);
    }

    @Override
    public String toString() {
      return "List<GeoCoordinate>";
    }

    private List<GeoCoordinate> interpretGeoposResult(List<Object> responses) {
      List<GeoCoordinate> responseCoordinate = new ArrayList<>(responses.size());
      for (Object response : responses) {
        if (response == null) {
          responseCoordinate.add(null);
        } else {
          List<Object> respList = (List<Object>) response;
          GeoCoordinate coord = new GeoCoordinate(DOUBLE.build(respList.get(0)),
              DOUBLE.build(respList.get(1)));
          responseCoordinate.add(coord);
        }
      }
      return responseCoordinate;
    }
  };

  public static final Builder<List<GeoRadiusResponse>> GEORADIUS_WITH_PARAMS_RESULT = new Builder<List<GeoRadiusResponse>>() {
    @Override
    public List<GeoRadiusResponse> build(Object data) {
      if (data == null) {
        return null;
      }

      List<Object> objectList = (List<Object>) data;

      List<GeoRadiusResponse> responses = new ArrayList<>(objectList.size());
      if (objectList.isEmpty()) {
        return responses;
      }

      if (objectList.get(0) instanceof List<?>) {
        // list of members with additional informations
        GeoRadiusResponse resp;
        for (Object obj : objectList) {
          List<Object> informations = (List<Object>) obj;

          resp = new GeoRadiusResponse((byte[]) informations.get(0));

          int size = informations.size();
          for (int idx = 1; idx < size; idx++) {
            Object info = informations.get(idx);
            if (info instanceof List<?>) {
              // coordinate
              List<Object> coord = (List<Object>) info;

              resp.setCoordinate(new GeoCoordinate(DOUBLE.build(coord.get(0)),
                  DOUBLE.build(coord.get(1))));
            } else if (info instanceof Long) {
              // score
              resp.setRawScore(LONG.build(info));
            } else {
              // distance
              resp.setDistance(DOUBLE.build(info));
            }
          }

          responses.add(resp);
        }
      } else {
        // list of members
        for (Object obj : objectList) {
          responses.add(new GeoRadiusResponse((byte[]) obj));
        }
      }

      return responses;
    }

    @Override
    public String toString() {
      return "GeoRadiusWithParamsResult";
    }
  };

  public static final Builder<List<Module>> MODULE_LIST = new Builder<List<Module>>() {
    @Override
    public List<Module> build(Object data) {
      if (data == null) {
        return null;
      }

      List<List<Object>> objectList = (List<List<Object>>) data;

      List<Module> responses = new ArrayList<>(objectList.size());
      if (objectList.isEmpty()) {
        return responses;
      }

      for (List<Object> moduleResp : objectList) {
        Module m = new Module(SafeEncoder.encode((byte[]) moduleResp.get(1)),
            ((Long) moduleResp.get(3)).intValue());
        responses.add(m);
      }

      return responses;
    }

    @Override
    public String toString() {
      return "List<Module>";
    }
  };

  /**
   * Create a AccessControlUser object from the ACL GETUSER < > result
   */
  public static final Builder<AccessControlUser> ACCESS_CONTROL_USER = new Builder<AccessControlUser>() {
    @Override
    public AccessControlUser build(Object data) {
      if (data == null) {
        return null;
      }

      List<List<Object>> objectList = (List<List<Object>>) data;
      if (objectList.isEmpty()) {
        return null;
      }

      AccessControlUser accessControlUser = new AccessControlUser();

      // flags
      List<Object> flags = objectList.get(1);
      for (Object f : flags) {
        accessControlUser.addFlag(SafeEncoder.encode((byte[]) f));
      }

      // passwords
      List<Object> passwords = objectList.get(3);
      for (Object p : passwords) {
        accessControlUser.addPassword(SafeEncoder.encode((byte[]) p));
      }

      // commands
      accessControlUser.setCommands(SafeEncoder.encode((byte[]) (Object) objectList.get(5)));

      // keys
      List<Object> keys = objectList.get(7);
      for (Object k : keys) {
        accessControlUser.addKey(SafeEncoder.encode((byte[]) k));
      }

      return accessControlUser;
    }

    @Override
    public String toString() {
      return "AccessControlUser";
    }

  };

  /**
   * Create an Access Control Log Entry Result of ACL LOG command
   */
  public static final Builder<List<AccessControlLogEntry>> ACCESS_CONTROL_LOG_ENTRY_LIST = new Builder<List<AccessControlLogEntry>>() {

    private final Map<String, Builder> mappingFunctions = createDecoderMap();

    private Map<String, Builder> createDecoderMap() {

      Map<String, Builder> tempMappingFunctions = new HashMap<>();
      tempMappingFunctions.put(AccessControlLogEntry.COUNT, LONG);
      tempMappingFunctions.put(AccessControlLogEntry.REASON, STRING);
      tempMappingFunctions.put(AccessControlLogEntry.CONTEXT, STRING);
      tempMappingFunctions.put(AccessControlLogEntry.OBJECT, STRING);
      tempMappingFunctions.put(AccessControlLogEntry.USERNAME, STRING);
      tempMappingFunctions.put(AccessControlLogEntry.AGE_SECONDS, STRING);
      tempMappingFunctions.put(AccessControlLogEntry.CLIENT_INFO, STRING);

      return tempMappingFunctions;
    }

    @Override
    public List<AccessControlLogEntry> build(Object data) {

      if (null == data) {
        return null;
      }

      List<AccessControlLogEntry> list = new ArrayList<>();
      List<List<Object>> logEntries = (List<List<Object>>) data;
      for (List<Object> logEntryData : logEntries) {
        Iterator<Object> logEntryDataIterator = logEntryData.iterator();
        AccessControlLogEntry accessControlLogEntry = new AccessControlLogEntry(
            createMapFromDecodingFunctions(logEntryDataIterator, mappingFunctions));
        list.add(accessControlLogEntry);
      }
      return list;
    }

    @Override
    public String toString() {
      return "List<AccessControlLogEntry>";
    }
  };

  // Stream Builders -->

  public static final Builder<StreamEntryID> STREAM_ENTRY_ID = new Builder<StreamEntryID>() {
    @Override
    public StreamEntryID build(Object data) {
      if (null == data) {
        return null;
      }
      String id = SafeEncoder.encode((byte[]) data);
      return new StreamEntryID(id);
    }

    @Override
    public String toString() {
      return "StreamEntryID";
    }
  };

  public static final Builder<List<StreamEntryID>> STREAM_ENTRY_ID_LIST = new Builder<List<StreamEntryID>>() {
    @Override
    @SuppressWarnings("unchecked")
    public List<StreamEntryID> build(Object data) {
      if (null == data) {
        return null;
      }
      List<Object> objectList = (List<Object>) data;
      List<StreamEntryID> responses = new ArrayList<>(objectList.size());
      if (!objectList.isEmpty()) {
        for(Object object : objectList) {
          responses.add(STREAM_ENTRY_ID.build(object));
        }
      }
      return responses;
    }
  };

  public static final Builder<StreamEntry> STREAM_ENTRY = new Builder<StreamEntry>() {
    @Override
    @SuppressWarnings("unchecked")
    public StreamEntry build(Object data) {
      if (null == data) {
        return null;
      }
      List<Object> objectList = (List<Object>) data;

      if (objectList.isEmpty()) {
        return null;
      }

      String entryIdString = SafeEncoder.encode((byte[]) objectList.get(0));
      StreamEntryID entryID = new StreamEntryID(entryIdString);
      List<byte[]> hash = (List<byte[]>) objectList.get(1);

      Iterator<byte[]> hashIterator = hash.iterator();
      Map<String, String> map = new HashMap<>(hash.size() / 2);
      while (hashIterator.hasNext()) {
        map.put(SafeEncoder.encode(hashIterator.next()), SafeEncoder.encode(hashIterator.next()));
      }
      return new StreamEntry(entryID, map);
    }

    @Override
    public String toString() {
      return "StreamEntry";
    }
  };

  public static final Builder<List<StreamEntry>> STREAM_ENTRY_LIST = new Builder<List<StreamEntry>>() {
    @Override
    @SuppressWarnings("unchecked")
    public List<StreamEntry> build(Object data) {
      if (null == data) {
        return null;
      }
      List<ArrayList<Object>> objectList = (List<ArrayList<Object>>) data;

      List<StreamEntry> responses = new ArrayList<>(objectList.size() / 2);
      if (objectList.isEmpty()) {
        return responses;
      }

      for (ArrayList<Object> res : objectList) {
        if (res == null) {
          responses.add(null);
          continue;
        }
        String entryIdString = SafeEncoder.encode((byte[]) res.get(0));
        StreamEntryID entryID = new StreamEntryID(entryIdString);
        List<byte[]> hash = (List<byte[]>) res.get(1);

        Iterator<byte[]> hashIterator = hash.iterator();
        Map<String, String> map = new HashMap<>(hash.size() / 2);
        while (hashIterator.hasNext()) {
          map.put(SafeEncoder.encode(hashIterator.next()), SafeEncoder.encode(hashIterator.next()));
        }
        responses.add(new StreamEntry(entryID, map));
      }

      return responses;
    }

    @Override
    public String toString() {
      return "List<StreamEntry>";
    }
  };

  public static final Builder<Map.Entry<StreamEntryID, List<StreamEntry>>> STREAM_AUTO_CLAIM_RESPONSE
      = new Builder<Map.Entry<StreamEntryID, List<StreamEntry>>>() {
    @Override
    @SuppressWarnings("unchecked")
    public Map.Entry<StreamEntryID, List<StreamEntry>> build(Object data) {
      if (null == data) {
        return null;
      }

      List<Object> objectList = (List<Object>) data;
      return new AbstractMap.SimpleEntry<>(STREAM_ENTRY_ID.build(objectList.get(0)),
          STREAM_ENTRY_LIST.build(objectList.get(1)));
    }

    @Override
    public String toString() {
      return "Map.Entry<StreamEntryID, List<StreamEntry>>";
    }
  };

  public static final Builder<Map.Entry<StreamEntryID, List<StreamEntryID>>> STREAM_AUTO_CLAIM_ID_RESPONSE
      = new Builder<Map.Entry<StreamEntryID, List<StreamEntryID>>>() {
    @Override
    @SuppressWarnings("unchecked")
    public Map.Entry<StreamEntryID, List<StreamEntryID>> build(Object data) {
      if (null == data) {
        return null;
      }

      List<Object> objectList = (List<Object>) data;
      return new AbstractMap.SimpleEntry<>(STREAM_ENTRY_ID.build(objectList.get(0)),
          STREAM_ENTRY_ID_LIST.build(objectList.get(1)));
    }

    @Override
    public String toString() {
      return "Map.Entry<StreamEntryID, List<StreamEntryID>>";
    }
  };

  public static final Builder<List<Map.Entry<String, List<StreamEntry>>>> STREAM_READ_RESPONSE
      = new Builder<List<Map.Entry<String, List<StreamEntry>>>>() {
    @Override
    public List<Map.Entry<String, List<StreamEntry>>> build(Object data) {
      if (data == null) {
        return null;
      }
      List<Object> streams = (List<Object>) data;

      List<Map.Entry<String, List<StreamEntry>>> result = new ArrayList<>(streams.size());
      for (Object streamObj : streams) {
        List<Object> stream = (List<Object>) streamObj;
        String streamId = SafeEncoder.encode((byte[]) stream.get(0));
        List<StreamEntry> streamEntries = BuilderFactory.STREAM_ENTRY_LIST.build(stream.get(1));
        result.add(new AbstractMap.SimpleEntry<>(streamId, streamEntries));
      }

      return result;
    }

    @Override
    public String toString() {
      return "List<Entry<String, List<StreamEntry>>>";
    }
  };

  public static final Builder<List<StreamPendingEntry>> STREAM_PENDING_ENTRY_LIST = new Builder<List<StreamPendingEntry>>() {
    @Override
    @SuppressWarnings("unchecked")
    public List<StreamPendingEntry> build(Object data) {
      if (null == data) {
        return null;
      }

      List<Object> streamsEntries = (List<Object>) data;
      List<StreamPendingEntry> result = new ArrayList<>(streamsEntries.size());
      for (Object streamObj : streamsEntries) {
        List<Object> stream = (List<Object>) streamObj;
        String id = SafeEncoder.encode((byte[]) stream.get(0));
        String consumerName = SafeEncoder.encode((byte[]) stream.get(1));
        long idleTime = BuilderFactory.LONG.build(stream.get(2));
        long deliveredTimes = BuilderFactory.LONG.build(stream.get(3));
        result.add(new StreamPendingEntry(new StreamEntryID(id), consumerName, idleTime,
            deliveredTimes));
      }
      return result;
    }

    @Override
    public String toString() {
      return "List<StreamPendingEntry>";
    }
  };

  public static final Builder<StreamInfo> STREAM_INFO = new Builder<StreamInfo>() {

    Map<String, Builder> mappingFunctions = createDecoderMap();

    private Map<String, Builder> createDecoderMap() {

      Map<String, Builder> tempMappingFunctions = new HashMap<>();
      tempMappingFunctions.put(StreamInfo.LAST_GENERATED_ID, STREAM_ENTRY_ID);
      tempMappingFunctions.put(StreamInfo.FIRST_ENTRY, STREAM_ENTRY);
      tempMappingFunctions.put(StreamInfo.LENGTH, LONG);
      tempMappingFunctions.put(StreamInfo.RADIX_TREE_KEYS, LONG);
      tempMappingFunctions.put(StreamInfo.RADIX_TREE_NODES, LONG);
      tempMappingFunctions.put(StreamInfo.LAST_ENTRY, STREAM_ENTRY);
      tempMappingFunctions.put(StreamInfo.GROUPS, LONG);

      return tempMappingFunctions;
    }

    @Override
    @SuppressWarnings("unchecked")
    public StreamInfo build(Object data) {
      if (null == data) {
        return null;
      }

      List<Object> streamsEntries = (List<Object>) data;
      Iterator<Object> iterator = streamsEntries.iterator();

      return new StreamInfo(createMapFromDecodingFunctions(iterator, mappingFunctions));
    }

    @Override
    public String toString() {
      return "StreamInfo";
    }
  };

  public static final Builder<List<StreamGroupInfo>> STREAM_GROUP_INFO_LIST = new Builder<List<StreamGroupInfo>>() {

    Map<String, Builder> mappingFunctions = createDecoderMap();

    private Map<String, Builder> createDecoderMap() {

      Map<String, Builder> tempMappingFunctions = new HashMap<>();
      tempMappingFunctions.put(StreamGroupInfo.NAME, STRING);
      tempMappingFunctions.put(StreamGroupInfo.CONSUMERS, LONG);
      tempMappingFunctions.put(StreamGroupInfo.PENDING, LONG);
      tempMappingFunctions.put(StreamGroupInfo.LAST_DELIVERED, STREAM_ENTRY_ID);

      return tempMappingFunctions;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<StreamGroupInfo> build(Object data) {
      if (null == data) {
        return null;
      }

      List<StreamGroupInfo> list = new ArrayList<>();
      List<Object> streamsEntries = (List<Object>) data;
      Iterator<Object> groupsArray = streamsEntries.iterator();

      while (groupsArray.hasNext()) {

        List<Object> groupInfo = (List<Object>) groupsArray.next();

        Iterator<Object> groupInfoIterator = groupInfo.iterator();

        StreamGroupInfo streamGroupInfo = new StreamGroupInfo(createMapFromDecodingFunctions(
          groupInfoIterator, mappingFunctions));
        list.add(streamGroupInfo);

      }
      return list;

    }

    @Override
    public String toString() {
      return "List<StreamGroupInfo>";
    }
  };

  public static final Builder<List<StreamConsumersInfo>> STREAM_CONSUMERS_INFO_LIST = new Builder<List<StreamConsumersInfo>>() {

    Map<String, Builder> mappingFunctions = createDecoderMap();

    private Map<String, Builder> createDecoderMap() {
      Map<String, Builder> tempMappingFunctions = new HashMap<>();
      tempMappingFunctions.put(StreamConsumersInfo.NAME, STRING);
      tempMappingFunctions.put(StreamConsumersInfo.IDLE, LONG);
      tempMappingFunctions.put(StreamGroupInfo.PENDING, LONG);
      tempMappingFunctions.put(StreamGroupInfo.LAST_DELIVERED, STRING);
      return tempMappingFunctions;

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<StreamConsumersInfo> build(Object data) {
      if (null == data) {
        return null;
      }

      List<StreamConsumersInfo> list = new ArrayList<>();
      List<Object> streamsEntries = (List<Object>) data;
      Iterator<Object> groupsArray = streamsEntries.iterator();

      while (groupsArray.hasNext()) {

        List<Object> groupInfo = (List<Object>) groupsArray.next();

        Iterator<Object> consumerInfoIterator = groupInfo.iterator();

        StreamConsumersInfo streamGroupInfo = new StreamConsumersInfo(
            createMapFromDecodingFunctions(consumerInfoIterator, mappingFunctions));
        list.add(streamGroupInfo);

      }
      return list;

    }

    @Override
    public String toString() {
      return "List<StreamConsumersInfo>";
    }
  };

  public static final Builder<StreamPendingSummary> STREAM_PENDING_SUMMARY = new Builder<StreamPendingSummary>() {
    @Override
    @SuppressWarnings("unchecked")
    public StreamPendingSummary build(Object data) {
      if (null == data) {
        return null;
      }

      List<Object> objectList = (List<Object>) data;
      long total = BuilderFactory.LONG.build(objectList.get(0));
      String minId = SafeEncoder.encode((byte[]) objectList.get(1));
      String maxId = SafeEncoder.encode((byte[]) objectList.get(2));
      List<List<Object>> consumerObjList = (List<List<Object>>) objectList.get(3);
      Map<String, Long> map = new HashMap<>(consumerObjList.size());
      for (List<Object> consumerObj : consumerObjList) {
        map.put(SafeEncoder.encode((byte[]) consumerObj.get(0)), Long.parseLong(SafeEncoder.encode((byte[]) consumerObj.get(1))));
      }
      return new StreamPendingSummary(total, new StreamEntryID(minId), new StreamEntryID(maxId), map);
    }

    @Override
    public String toString() {
      return "StreamPendingSummary";
    }
  };

  private static Map<String, Object> createMapFromDecodingFunctions(Iterator<Object> iterator,
      Map<String, Builder> mappingFunctions) {

    Map<String, Object> resultMap = new HashMap<>();
    while (iterator.hasNext()) {

      String mapKey = STRING.build(iterator.next());
      if (mappingFunctions.containsKey(mapKey)) {
        resultMap.put(mapKey, mappingFunctions.get(mapKey).build(iterator.next()));
      } else { // For future - if we don't find an element in our builder map
        Object unknownData = iterator.next();
        for (Builder b : mappingFunctions.values()) {
          try {
            resultMap.put(mapKey, b.build(unknownData));
            break;
          } catch (ClassCastException e) {
            // We continue with next builder

          }
        }
      }
    }
    return resultMap;
  }

  // <-- Stream Builders

  public static final Builder<LCSMatchResult> STR_ALGO_LCS_RESULT_BUILDER = new Builder<LCSMatchResult>() {
    @Override
    public LCSMatchResult build(Object data) {
      if (data == null) {
        return null;
      }

      if (data instanceof byte[]) {
        return new LCSMatchResult(STRING.build(data));
      } else if (data instanceof Long) {
        return new LCSMatchResult(LONG.build(data));
      } else {
        long len = 0;
        List<MatchedPosition> matchedPositions = new ArrayList<>();

        List<Object> objectList = (List<Object>) data;
        if ("matches".equalsIgnoreCase(STRING.build(objectList.get(0)))) {
          List<Object> matches = (List<Object>)objectList.get(1);
          for (Object obj : matches) {
            if (obj instanceof List<?>) {
              List<Object> positions = (List<Object>) obj;
              Position a = new Position(
                  LONG.build(((List<Object>) positions.get(0)).get(0)),
                  LONG.build(((List<Object>) positions.get(0)).get(1))
              );
              Position b = new Position(
                  LONG.build(((List<Object>) positions.get(1)).get(0)),
                  LONG.build(((List<Object>) positions.get(1)).get(1))
              );
              long matchLen = 0;
              if (positions.size() >= 3) {
                matchLen = LONG.build(positions.get(2));
              }
              matchedPositions.add(new MatchedPosition(a, b, matchLen));
            }
          }
        }

        if ("len".equalsIgnoreCase(STRING.build(objectList.get(2)))) {
          len = LONG.build(objectList.get(3));
        }
        return new LCSMatchResult(matchedPositions, len);
      }
    }
  };

  public static final Builder<Map<String, String>> STRING_MAP_FROM_PAIRS = new Builder<Map<String, String>>() {
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, String> build(Object data) {
      final List<Object> list = (List<Object>) data;
      final Map<String, String> map = new HashMap<>(list.size());
      for (Object object : list) {
        final List<byte[]> flat = (List<byte[]>) object;
        map.put(SafeEncoder.encode(flat.get(0)), flat.get(1) != null ? SafeEncoder.encode(flat.get(1)) : null);
      }

      return map;
    }

    @Override
    public String toString() {
      return "Map<String, String>";
    }

  };

  public static final Builder<Class<?>> JSON_TYPE = new Builder<Class<?>>() {
    @Override
    public Class<?> build(Object data) {
      if (data == null) return null;
      String str = STRING.build(data);
      switch (str) {
        case "null":
          return null;
        case "boolean":
          return boolean.class;
        case "integer":
          return int.class;
        case "number":
          return float.class;
        case "string":
          return String.class;
        case "object":
          return Object.class;
        case "array":
          return List.class;
        default:
          throw new JedisException("Unknown type: " + str);
      }
    }

    @Override
    public String toString() {
      return "Class<?>";
    }
  };

  public static final Builder<List<Class<?>>> JSON_TYPE_LIST = new Builder<List<Class<?>>>() {
    @Override
    public List<Class<?>> build(Object data) {
      List<Object> list = (List<Object>) data;
      List<Class<?>> classes = new ArrayList<>(list.size());
      for (Object elem : list) {
        try {
          classes.add(JSON_TYPE.build(elem));
        } catch (JedisException je) {
          classes.add(null);
        }
      }
      return classes;
    }
  };

  public static final Builder<Object> JSON_OBJECT = new Builder<Object>() {
    @Override
    public Object build(Object data) {
      if (data == null) return null;

      if (!(data instanceof byte[])) return data;

      String str = STRING.build(data);
      if (str.charAt(0) == '{') {
        try {
          return new JSONObject(str);
        } catch (Exception ex) { }
      } else if (str.charAt(0) == '[') {
        try {
          return new JSONArray(str);
        } catch (Exception ex) { }
      }
      return str;
    }
  };

  public static final Builder<JSONArray> JSON_ARRAY = new Builder<JSONArray>() {
    @Override
    public JSONArray build(Object data) {
      if (data == null) return null;
      return new JSONArray(STRING.build(data));
    }
  };

  public static final Builder<List<JSONArray>> JSON_ARRAY_LIST = new Builder<List<JSONArray>>() {
    @Override
    public List<JSONArray> build(Object data) {
      if (data == null) return null;
      List<Object> list = (List<Object>) data;
      return list.stream().map(o -> JSON_ARRAY.build(o)).collect(Collectors.toList());
    }
  };

  public static final Builder<AggregationResult> SEARCH_AGGREGATION_RESULT = new Builder<AggregationResult>() {
    @Override
    public AggregationResult build(Object data) {
      return new AggregationResult(data);
    }
  };

  public static final Builder<AggregationResult> SEARCH_AGGREGATION_RESULT_WITH_CURSOR = new Builder<AggregationResult>() {
    @Override
    public AggregationResult build(Object data) {
      List<Object> list = (List<Object>) data;
      return new AggregationResult(list.get(0), (long) list.get(1));
    }
  };

  public static final Builder<Map<String, List<String>>> SEARCH_SYNONYM_GROUPS = new Builder<Map<String, List<String>>>() {
    @Override
    public Map<String, List<String>> build(Object data) {
      List<Object> list = (List<Object>) data;
      Map<String, List<String>> dump = new HashMap<>(list.size() / 2);
      for (int i = 0; i < list.size(); i += 2) {
        dump.put(STRING.build(list.get(i)), STRING_LIST.build(list.get(i + 1)));
      }
      return dump;
    }
  };

  /**
   * A decorator to implement Set from List. Assume that given List do not contains duplicated
   * values. The resulting set displays the same ordering, concurrency, and performance
   * characteristics as the backing list. This class should be used only for Redis commands which
   * return Set result.
   * @param <E>
   */
  protected static class SetFromList<E> extends AbstractSet<E> implements Serializable {
    private static final long serialVersionUID = -2850347066962734052L;
    private final List<E> list;

    private SetFromList(List<E> list) {
      this.list = list;
    }

    @Override
    public void clear() {
      list.clear();
    }

    @Override
    public int size() {
      return list.size();
    }

    @Override
    public boolean isEmpty() {
      return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
      return list.contains(o);
    }

    @Override
    public boolean remove(Object o) {
      return list.remove(o);
    }

    @Override
    public boolean add(E e) {
      return !contains(e) && list.add(e);
    }

    @Override
    public Iterator<E> iterator() {
      return list.iterator();
    }

    @Override
    public Object[] toArray() {
      return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
      return list.toArray(a);
    }

    @Override
    public String toString() {
      return list.toString();
    }

    @Override
    public int hashCode() {
      return list.hashCode();
    }

    @Override
    public boolean equals(Object o) {
      if (o == null) return false;
      if (o == this) return true;
      if (!(o instanceof Set)) return false;

      Collection<?> c = (Collection<?>) o;
      if (c.size() != size()) {
        return false;
      }

      return containsAll(c);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
      return list.containsAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
      return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
      return list.retainAll(c);
    }

    protected static <E> SetFromList<E> of(List<E> list) {
      if (list == null) {
        return null;
      }
      return new SetFromList<>(list);
    }
  }

  private BuilderFactory() {
    throw new InstantiationError("Must not instantiate this class");
  }

}
