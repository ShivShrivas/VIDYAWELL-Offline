package vidyawell.infotech.bsn.admin.Helpers;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

public class Upload_Document_Helper extends RecyclerView.Adapter {


    public String DocumentId;
    public String DocumentName;
    public String EmployeeCode;
    public String EmpName;
    public String IsLocked;
    public String DocumentPath;
    public String IsFileType;
    public String getLoginType;




    //DocumentId,DocumentName,EmployeeCode,EmpName,IsLocked,DocumentPath

    public Upload_Document_Helper(String DocumentId, String DocumentName, String EmployeeCode, String EmpName,String IsLocked,String DocumentPath,String IsFileType,String getLoginType){
        this.DocumentId=DocumentId;
        this.DocumentName=DocumentName;
        this.EmployeeCode=EmployeeCode;
        this.EmpName=EmpName;
        this.IsLocked=IsLocked;
        this.DocumentPath=DocumentPath;
        this.IsFileType=IsFileType;
        this.getLoginType=getLoginType;


    }





    public String getDocumentId() {
        return DocumentId;
    }

    public void setDocumentId(String DocumentId) {
        this. DocumentId = DocumentId;
    }

    public String getDocumentName() {
        return DocumentName;
    }
    public void setDocumentName(String DocumentName) {
        this. DocumentName = DocumentName;
    }

    public String getEmployeeCode() {
        return EmployeeCode;
    }
    public void setEmployeeCode(String EmployeeCode) {
        this. EmployeeCode = EmployeeCode;
    }

    public String getEmpName() {
        return EmpName;
    }
    public void setEmpName(String EmpName) {
        this. EmpName = EmpName;
    }

    public String getIsLocked() {
        return IsLocked;
    }
    public void setIsLocked(String IsLocked) {
        this. IsLocked = IsLocked;
    }

    public String getDocumentPath() {
        return DocumentPath;
    }
    public void setDocumentPath(String DocumentPath) {
        this. DocumentPath = DocumentPath;
    }

    public String getIsFileType() {
        return IsFileType;
    }
    public void setIsFileType(String IsFileType) {
        this. IsFileType = IsFileType;
    }


    public String getgetLoginType() {
        return getLoginType;
    }
    public void setgetLoginType(String getLoginType) {
        this. getLoginType = getLoginType;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
