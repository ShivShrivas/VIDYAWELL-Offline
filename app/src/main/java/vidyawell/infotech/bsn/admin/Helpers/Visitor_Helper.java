package vidyawell.infotech.bsn.admin.Helpers;

public class Visitor_Helper {

    String status,TransId,photo,VisitorName,Purposes,HOWUKNOWs,AnyReference,OrganizationName,Address,VisitTypes,EmailId,PhoneNo,TotalPassMember,OfficeEmail;


    public Visitor_Helper(String status,String TransId, String photo, String VisitorName,String Purposes,String HOWUKNOWs ,String AnyReference,String OrganizationName,String Address,String VisitTypes,String EmailId,String PhoneNo,String TotalPassMember,String OfficeEmail){
        this.TransId=TransId;
        this.photo=photo;
        this.VisitorName=VisitorName;
        this.Purposes=Purposes;
        this.HOWUKNOWs=HOWUKNOWs;
        this.AnyReference=AnyReference;
        this.OfficeEmail=OfficeEmail;
        this.OrganizationName=OrganizationName;
        this.Address=Address;
        this.VisitTypes=VisitTypes;
        this.EmailId=EmailId;
        this.PhoneNo=PhoneNo;
        this.TotalPassMember=TotalPassMember;

        this.status=status;

    }



    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this. status = status;
    }

    public String getTransId() {
        return TransId;
    }

    public void setTransId(String TransId) {
        this. TransId = TransId;
    }

    public String getVphoto() {
        return photo;
    }

    public void setVphoto(String photo) {
        this. photo = photo;
    }

    public String getVisitorName() {
        return VisitorName;
    }

    public void setVisitorName(String VisitorName) {
        this. VisitorName = VisitorName;
    }

    public String getPurposes() {
        return Purposes;
    }

    public void setPurposes(String Purposes) {
        this. Purposes = Purposes;
    }

    public String getHOWUKNOWs() {
        return HOWUKNOWs;
    }

    public void setHOWUKNOWs(String HOWUKNOWs) {
        this. HOWUKNOWs = HOWUKNOWs;
    }

    public String getAnyReference() {
        return AnyReference;
    }

    public void setAnyReference(String AnyReference) {
        this. AnyReference = AnyReference;
    }

    public String getOfficeEmail() {
        return OfficeEmail;
    }

    public void setOfficeEmail(String OfficeEmail) {
        this. OfficeEmail = OfficeEmail;
    }

    public String getOrganizationName() {
        return OrganizationName;
    }

    public void setOrganizationName(String OrganizationName) {
        this. OrganizationName = OrganizationName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this. Address = Address;
    }

    public String getVisitTypes() {
        return VisitTypes;
    }

    public void setVisitTypes(String VisitTypes) {
        this. VisitTypes = VisitTypes;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String EmailId) {
        this. EmailId = EmailId;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String PhoneNo) {
        this. PhoneNo = PhoneNo;
    }

    public String getTotalPassMember() {
        return TotalPassMember;
    }

    public void setTotalPassMember(String TotalPassMember) {
        this. TotalPassMember = TotalPassMember;
    }

}
