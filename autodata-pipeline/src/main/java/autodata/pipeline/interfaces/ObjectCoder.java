package autodata.pipeline.interfaces;

import lombok.RequiredArgsConstructor;
import org.apache.beam.sdk.coders.Coder;
import org.apache.beam.sdk.coders.CoderException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(staticName = "of")
public class ObjectCoder<Element> extends Coder<Element> {
  private final Class<Element> targetClass;

  @Override
  public void encode(Element value, OutputStream outStream) throws CoderException, IOException {
    outStream.write(this.toByteArray(value));
  }

  @Override
  public Element decode(InputStream inStream) throws CoderException, IOException {
    return this.toElement(inStream);
  }

  @Override
  public List<? extends Coder<?>> getCoderArguments() {
    return new ArrayList<>();
  }

  @Override
  public void verifyDeterministic() throws NonDeterministicException {

  }

  private Element toElement(InputStream inputStream) throws IOException {
    try {
      ObjectInputStream objectStream = new ObjectInputStream(inputStream);
      return (Element) objectStream.readObject();
    } catch (ClassNotFoundException e) {
      //Impossible to happen
      throw new IOException(e);
    }
  }

  private byte[] toByteArray(Element value) throws IOException {
    try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
         ObjectOutputStream objectStream = new ObjectOutputStream(byteStream)
    ) {
      objectStream.writeObject(value);
      return byteStream.toByteArray();
    }
  }
}
