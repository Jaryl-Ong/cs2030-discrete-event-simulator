import java.util.Comparator;

public interface ServerComparators extends Comparator<Server> {
  public int compare(Server a, Server b);

  public boolean equals(Object obj);
}