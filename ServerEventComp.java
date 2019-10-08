class ServerEventComp extends EventComp {
  // compares events first based on timing then on event type
  public int compare(Event a, Event b) {
    if (a.getCustomerType() != b.getCustomerType()) {
      return a.getCustomerType().compareTo(b.getCustomerType());
    } else
      return super.compare(a, b);
  }
}