package carrental;

import carrental.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired ClientRepository clientRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReservated_SaveClient(@Payload Reservated reservated){

        if(!reservated.validate()) return;

        System.out.println("\n\n##### listener SaveClient : " + reservated.toJson() + "\n\n");
        System.out.println("################### 1111111111 reservated.toJson() ##############################" + reservated.toJson());

        if ("Reservated".equals(reservated.getEventType())) {

            if(!reservated.validate()) return;

            // Sample Logic //
            Client client = new Client();
            client.setContractId(reservated.getContractId());
            client.setCustName(reservated.getCustName());
            client.setModelName(reservated.getModelName());
            client.setReservationStatus(reservated.getReservationStatus());
            
            clientRepository.save(client);

        } else {
            System.out.println("################### Client / PolicyHandler >> PUB/SUB 예약 호출 pass ##############################");
        }


        // Sample Logic //
        // Client client = new Client();
        // clientRepository.save(client);

    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReservationCanceled_DeleteClient(@Payload ReservationCanceled reservationCanceled){

        if(!reservationCanceled.validate()) return;

        System.out.println("\n\n##### listener DeleteClient : " + reservationCanceled.toJson() + "\n\n");

        if(!reservationCanceled.validate()) return;

        System.out.println("\n\n##### listener SaveClient : " + reservationCanceled.toJson() + "\n\n");
        System.out.println("################### 1111111111 reservated.toJson() ##############################" + reservationCanceled.toJson());

        if ("ReservationCanceled".equals(reservationCanceled.getEventType())) {

            if(!reservationCanceled.validate()) return;

            // Sample Logic //
            Client client = new Client();
            client.setContractId(reservationCanceled.getContractId());
            client.setCustName(reservationCanceled.getCustName());
            client.setModelName(reservationCanceled.getModelName());
            client.setReservationStatus(reservationCanceled.getReservationStatus());
            
            clientRepository.save(client);

        } else {
            System.out.println("################### Client / PolicyHandler >> PUB/SUB 예약 호출 pass ##############################");
        }

        // Sample Logic //
        // Client client = new Client();
        // clientRepository.save(client);

    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
