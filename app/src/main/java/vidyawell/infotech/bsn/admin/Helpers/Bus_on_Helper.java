package vidyawell.infotech.bsn.admin.Helpers;

public class Bus_on_Helper {
    String VehicleId;
    String vacle_number;
    String track_time;
    String driver_name;
    String bus_stop;


    // VehicleId,VehicleNo,OwnerName,OwnerAddress,VehicleType
   public   Bus_on_Helper(String VehicleId,String VehicleNo,String VehicleType,String OwnerName,String OwnerAddress){
       this.VehicleId=VehicleId;
       this.vacle_number=VehicleNo;
       this.track_time=VehicleType;
       this.driver_name=OwnerName;
       this.bus_stop=OwnerAddress;



   }

    public String getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(String VehicleId) {
        this.VehicleId = VehicleId;
    }




    public String getVehicleNo() {
        return vacle_number;
    }

    public void setVehicleNo(String VehicleNo) {
        this.vacle_number = VehicleNo;
    }

    public String geVehicleType(){
     return  track_time;
    }

    public  void setVehicleType(String VehicleType){
       this.track_time = VehicleType;
    }


    public String getOwnerName() {
        return driver_name;
    }

    public void setOwnerName(String OwnerName) {
        this.driver_name = OwnerName;
    }

    public String getOwnerAddress() {
        return bus_stop;
    }

    public void setOwnerAddress(String OwnerAddress) {
        this.bus_stop = OwnerAddress;
    }


}
