package no.guttab.raven.annotations;

public enum StreamType {
   URL("stream.url"),
   BODY("stream.body");
   private String streamParameterKey;

   StreamType(String streamParameterKey) {
      this.streamParameterKey = streamParameterKey;
   }

   public String getStreamParameterKey() {
      return streamParameterKey;
   }
}
