public class ServerQueueComp implements ServerComparators {
  public int compare(Server a, Server b) {
    if (a.getQueueSize() != b.getQueueSize()) {
      return a.getQueueSize() - b.getQueueSize();
    } else if (a.getElderly() != b.getElderly()) {
      return a.getElderly() - b.getElderly();
    } else
      return a.getID() - b.getID();
  }

  public boolean equals(Object obj) {
    return this == obj;
  }
}