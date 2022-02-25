package vidyawell.infotech.bsn.admin.CalendarActivity;


import vidyawell.infotech.bsn.admin.Calendar.CaldroidFragment;
import vidyawell.infotech.bsn.admin.Calendar.CaldroidGridAdapter;

public class CaldroidSampleCustomFragment extends CaldroidFragment {

	@Override
	public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
		// TODO Auto-generated method stub
		return new CaldroidSampleCustomAdapter(getActivity(), month, year,
				getCaldroidData(), extraData);
	}

}
