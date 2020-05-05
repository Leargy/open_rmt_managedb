package receiver;

import communication.ClientPackage;
import communication.Mediating;
import communication.Segment;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;

/**
 * Класс получатель, в метод принимает посылку от сервера и десериализует в пригодный для обработки вид.
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
public class Receiver extends AReceiver{
    private ByteArrayInputStream byteArrayInputStream;
    private ObjectInputStream objectInputStream;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(2*1024);

    public Receiver(Mediating mediator){
        super(mediator);
    }

    /**
     * метод receive() - метод класса родителя, ответственене за десериализацию.
     * @param parcel
     */
    @Override
    public void receive(Segment parcel) {
        try {
            byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            ClientPackage query = (ClientPackage) objectInputStream.readObject();
            parcel.setDataObject(query);
            mediator.notify(this,parcel);
        }catch (IOException e) {
            e.printStackTrace();
            //TODO:write an handling to this type of error
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
            //TODO:write an handling to this type of error
        }
    }
}