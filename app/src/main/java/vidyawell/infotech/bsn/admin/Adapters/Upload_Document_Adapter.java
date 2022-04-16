package vidyawell.infotech.bsn.admin.Adapters;


import android.content.Context;


import android.graphics.Typeface;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import vidyawell.infotech.bsn.admin.Helpers.Upload_Document_Helper;
import vidyawell.infotech.bsn.admin.R;
import vidyawell.infotech.bsn.admin.ServerApis.ServerApiadmin;


public  class Upload_Document_Adapter extends RecyclerView.Adapter<Upload_Document_Adapter.MyViewHolder> {

    private List<Upload_Document_Helper> upload_document_helpers;
    Context context;
    Typeface typeFace;

    customButtonListener customListner;
    customButtonListener2 customListner2;

    public interface customButtonListener {
        public void onButtonClickListner(int position, Upload_Document_Helper value);
    }

    public void setcustomButtonListener(customButtonListener listener) {
        this.customListner = listener;
    }



    public interface customButtonListener2 {
        public void onButtonClickListner2(int position, Upload_Document_Helper value);
    }

    public void setcustomButtonListener2(customButtonListener listener) {
        this.customListner2 = (customButtonListener2) listener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView   text_id_name,close_document_id,image_id,login_type_id;
        ImageView delete_member;
        ImageView image_upload;
        TextView document_id,file_type;
        Button lock_status;




        public MyViewHolder(View view) {
            super(view);


            text_id_name = (TextView) itemView.findViewById(R.id.text_id_name);
            delete_member = (ImageView) itemView.findViewById(R.id.delete_member);
            close_document_id =itemView.findViewById(R.id.close_document_id);
            image_upload=(ImageView)itemView.findViewById(R.id.image_upload);
            image_id=(TextView) itemView.findViewById(R.id.image_id);
            document_id=(TextView) itemView.findViewById(R.id.document_id);
            file_type=(TextView) itemView.findViewById(R.id.file_type);
            lock_status=(Button) itemView.findViewById(R.id.lock_status);
            login_type_id=(TextView) itemView.findViewById(R.id.login_type_id);



            typeFace = Typeface.createFromAsset(text_id_name.getContext().getAssets(),
                    "fonts/" + ServerApiadmin.FONT);

        }

    }


    public Upload_Document_Adapter(Context context, int item_upload_document, List<Upload_Document_Helper> upload_document_helpers) {
        this.upload_document_helpers = upload_document_helpers;
        this.context=context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_upload_document, parent, false);

        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Upload_Document_Helper appointmentClose_helper = upload_document_helpers.get(position);


        holder.text_id_name.setText(appointmentClose_helper.getDocumentName());
        holder.document_id.setText(appointmentClose_helper.getDocumentId());
        holder.image_id.setText(appointmentClose_helper.getDocumentPath());
        holder.file_type.setText(appointmentClose_helper.getIsFileType());
        holder.login_type_id.setText(appointmentClose_helper.getgetLoginType());




       /* if(appointmentClose_helper.getLoginType.equalsIgnoreCase("4")){
            //emp
        }else {
            ///admin
        }
*/


        if (appointmentClose_helper.getIsLocked().equalsIgnoreCase("false")){
            holder.lock_status.setBackgroundResource(R.drawable.ic_absent_list);
        }else {
            holder.lock_status.setBackgroundResource(R.drawable.ic_present);

        }


      /*  String imagerplace= ServerApiadmin.MAIN_IPLINK+appointmentClose_helper.getDocumentPath();
        imagerplace=imagerplace.replace("..","");
        byte[]   imageBytes = Base64.decode(appointmentClose_helper.getDocumentPath(), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.image_upload.setImageBitmap(decodedImage);
        */

/*

                if(appointmentClose_helper.getDocumentPath().length()>50){
                    byte[]   imageBytes = Base64.decode(appointmentClose_helper.getDocumentPath(), Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    holder.image_upload.setImageBitmap(decodedImage);

                }else {
                    holder.image_upload.setImageResource(R.drawable.ic_user_image);
                }

*/


           if(appointmentClose_helper.getIsFileType().equalsIgnoreCase("1")){
               String imagerplace= ServerApiadmin.MAIN_IPLINK+appointmentClose_helper.getDocumentPath();
               imagerplace=imagerplace.replace("..","");
               holder.image_upload.setImageURI(Uri.parse(imagerplace));
               Glide.get(context).clearMemory();

               Glide
                       .with(context)
                       .load(imagerplace)
                       .apply(new RequestOptions()
                               .diskCacheStrategy(DiskCacheStrategy.NONE)
                               .skipMemoryCache(true))
                       .into(holder.image_upload);

           }else {
               String imagerplace= ServerApiadmin.MAIN_IPLINK+appointmentClose_helper.getDocumentPath();
               imagerplace=imagerplace.replace("..","");
               holder.file_type.setText(imagerplace);
               holder.image_upload.setImageResource(R.drawable.ic_pdf);


           }







        // feeCard_helper.notifyItemChanged(position);

        holder.text_id_name.setTypeface(typeFace);




        final Upload_Document_Helper temp = upload_document_helpers.get(position);
        holder.delete_member.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (customListner != null) {
                    customListner.onButtonClickListner(position, temp);
                    //Toast.makeText(v.getContext(), "Clicked delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customListner2 != null) {
                    customListner2.onButtonClickListner2(position, temp);
                    //Toast.makeText(v.getContext(), "Clicked delete", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


    @Override
    public int getItemCount() {
        return upload_document_helpers.size();

    }
}
