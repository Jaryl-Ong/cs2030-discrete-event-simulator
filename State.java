import java.util.function.BiFunction;

enum State{
  BACK(Event::processBack), 
  REST(Event::processRest), 
  DONE(Event::processDone), 
  LEAVES(Event::processLeaves), 
  SERVED(Event::processServed), 
  WAITS(Event::processWaits), 
  ARRIVES(Event::processArrives), 
  UNKNOWN(Event::processUnknown);
  public final BiFunction<Event, Event, Event> processEvent;

  private State(BiFunction<Event, Event, Event> processEvent){
    this.processEvent = processEvent;
  }
}