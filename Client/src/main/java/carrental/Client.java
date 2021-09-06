package carrental;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Client_table")
public class Client {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private long contractId;
    private String custName;
    private String modelName;
    private String reservationStatus;

    @PostPersist
    public void onPostPersist(){
        ClientSaved clientSaved = new ClientSaved();
        BeanUtils.copyProperties(this, clientSaved);



        //로직구현이 필요함
        System.out.println("######################### 신규 메소드 호출되었음. #############################");

        clientSaved.publishAfterCommit();

    }
    @PostUpdate
    public void onPostUpdate(){
        ClientDeleted clientDeleted = new ClientDeleted();
        BeanUtils.copyProperties(this, clientDeleted);



        //로직구현이 필요함
        System.out.println("######################### 신규 메소드 호출되었음. #############################");
        this.setContractId(this.getId());
        PayCanceled payCanceled = new PayCanceled();
        BeanUtils.copyProperties(this, payCanceled);
        payCanceled.setPaystatus("payCanceled");
        System.out.println("################### PAY  >> 계약 취소 / 결제취소 호출 payCanceled.toJson() ############################# " + payCanceled.toJson() );
        
        payCanceled.publish();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        Reservation reservation = new Reservation();
        BeanUtils.copyProperties(this, reservation);
        
        reservation.setPayStatus("payCanceled");



        clientDeleted.publishAfterCommit();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }
    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }




}