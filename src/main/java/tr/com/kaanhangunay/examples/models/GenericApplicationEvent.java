package tr.com.kaanhangunay.examples.models;

import java.util.UUID;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

public record GenericApplicationEvent<T>(
    UUID messageId, QueueMessageType type, T body, String source)
    implements ResolvableTypeProvider {
  @Override
  public ResolvableType getResolvableType() {
    return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(this.body));
  }
}
