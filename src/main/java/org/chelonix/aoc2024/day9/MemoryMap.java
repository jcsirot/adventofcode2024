package org.chelonix.aoc2024.day9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class MemoryMap {
  private static final int[] ZEROS = new int[10];

  private int[] memoryMap;
  private Map<Integer, IndexAndSize> indexAndSizeMap = new HashMap<>();

  private MemoryMap(int[] memoryMap, Map<Integer, IndexAndSize> indexAndSizeMap) {
    this.memoryMap = memoryMap;
    this.indexAndSizeMap = indexAndSizeMap;
  }

  public long checksum() {
    return IntStream.range(0, memoryMap.length)
        .mapToLong(i -> memoryMap[i] == 0 ? 0 : i * (memoryMap[i] - 1))
        .sum();
  }

  public void compactAndFragment() {
mainloop:
    while(true) {
      for (int i = memoryMap.length - 1; i >= 0; i--) {
        int id = memoryMap[i];
        if (id != 0) {
          for (int j = 0; j < i; j++) {
            if (memoryMap[j] == 0) {
              memoryMap[i] = 0;
              memoryMap[j] = id;
              continue mainloop;
            }
          }
        }
      }
      return;
    }
  }

  public void compactSmart() {
    int currentFile = indexAndSizeMap.keySet().stream().max(Integer::compareTo).get();
    while (currentFile >= 0) {
      int fSize = indexAndSizeMap.get(currentFile).size();
      int fIndex = indexAndSizeMap.get(currentFile).index();
      int index = findFreeSpace(fSize, fIndex);
      if (index != -1) {
        System.arraycopy(memoryMap, fIndex, memoryMap, index, fSize);
        System.arraycopy(ZEROS, 0, memoryMap, fIndex, fSize);
      }
      currentFile--;
    }
  }

  private int findFreeSpace(int size, int index) {
    for (int i = 0; i < index; i++) {
      if (memoryMap[i] != 0) {
        continue;
      }
      if (IntStream.range(i, i+size).map(j -> memoryMap[j]).allMatch(j -> j == 0)) {
        return i;
      }
    }
    return -1;
  }

  public static class Builder {
    private List<Integer> memoryMap = new ArrayList<>();
    private Map<Integer, IndexAndSize> indexAndSizeMap = new HashMap<>();

    public void addFile(int id, int size) {
      indexAndSizeMap.put(id, new IndexAndSize(memoryMap.size(), size));
      for (int i = 0; i < size; i++) {
        memoryMap.add(id+1);
      }
    }

    public void addFree(int size) {
      for (int i = 0; i < size; i++) {
        memoryMap.add(0);
      }
    }

    public MemoryMap build() {
      return new MemoryMap(memoryMap.stream().mapToInt(i -> i).toArray(), indexAndSizeMap);
    }
  }
}
