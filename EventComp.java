import java.util.Comparator;

public class EventComp implements Comparator<Event> {
  // compares events first based on timing then on event type

  public int compare(Event a, Event b) {
    if (a.getTime() != b.getTime()) {
      if (a.getTime() > b.getTime())
        return 1;
      else
        return -1;
    } else {
      return a.getEventType().compareTo(b.getEventType());
    }
  }

  // not very useful
  public boolean equals(Object obj) {
    return this == obj;
  }
}
