package vidyawell.infotech.bsn.admin.Helpers;

public class Timetable_Helper {
        public String sub;
        public String tech;
        public int imageid;
        public String getLecture,PeriodType;

        public Timetable_Helper(String sub, String tech, String getLecture,int imageid,String PeriodType ){
            this.sub=sub;
            this.tech=tech;
            this.imageid=imageid;
            this.getLecture=getLecture;
            this.PeriodType=PeriodType;

        }

    public String getPeriodType() {
        return PeriodType;
    }

    public void setPeriodType(String PeriodType) {
        this. PeriodType = PeriodType;
    }


        public String getHomelistvalue() {
            return sub;
        }

        public void setHomelistvalue(String sub) {
            this. sub = sub;
        }

        public String getdescvalue() {
            return tech;
        }
        public void setdescvalue(String tech) {
            this. tech = tech;
        }

        public int  getimagevalue() {
            return imageid;
        }
        public void setimagevalue(int imageid) {
            this. imageid = imageid;
        }

    public String getlecvalue() {
        return getLecture;
    }
    public void setlecvalue(String getLecture) {
        this. getLecture = getLecture;
    }
}
