package vidyawell.infotech.bsn.admin.Helpers;

public class ClassList_Checkbox_Helper {
    private  String ClassName="";
    private  String ClassID="";
    private boolean selected;



    public void setClassName(String ClassName)
    {
        this.ClassName = ClassName;
    }
    public String getClassName()
    {
        return this.ClassName;
    }

    public void setClassID(String ClassID)
    {
        this.ClassID = ClassID;
    }
    public String getClassID()
    {
        return this.ClassID;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
