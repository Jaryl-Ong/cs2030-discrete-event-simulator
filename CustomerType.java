import java.util.ArrayList;
import java.util.Collections;

public enum CustomerType {
  ELDERLY {
    @Override
    public Server findPreferredQueue(ArrayList<Server> freeQueues) {
      Collections.sort(freeQueues, new ServerComp());
      return freeQueues.get(0);
    }

    @Override
    public Server findPreferredServer(ArrayList<Server> freeServers) {
      Collections.sort(freeServers, new ServerComp());
      return freeServers.get(0);
    }
  },
  GENERAL {
    @Override
    public Server findPreferredQueue(ArrayList<Server> freeQueues) {
      Collections.sort(freeQueues, new ServerQueueComp());
      return freeQueues.get(0);
    }

    @Override
    public Server findPreferredServer(ArrayList<Server> freeServers) {
      Collections.sort(freeServers, new ServerComp());
      return freeServers.get(0);
    }
  };

  public abstract Server findPreferredQueue(ArrayList<Server> freeQueues);

  public abstract Server findPreferredServer(ArrayList<Server> freeServers);
}