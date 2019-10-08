public class ServerComp implements ServerComparators {
  public int compare(Server a, Server b) {
    return a.getID() - b.getID();
  }

  public boolean equals(Object obj) {
    return this == obj;
  }
}


