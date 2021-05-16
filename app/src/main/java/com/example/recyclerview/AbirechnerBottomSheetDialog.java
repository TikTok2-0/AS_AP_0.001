package com.hlgkaifu.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.viewpager2.widget.ViewPager2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;


public class AbirechnerBottomSheetDialog extends BottomSheetDialogFragment {

    private com.hlgkaifu.recyclerview.AbirechnerActivity abirechnerActivity;
    private Context context;
    private boolean isEdtpage;
    private int positionAbinot;
    private com.hlgkaifu.recyclerview.AbiNote abinote;

    private TextView headline;

    private EditText edtTxtCourse;

    private TextView txtPointsS1;
    private TextView txtPointsS2;
    private TextView txtPointsS3;
    private TextView txtPointsS4;
    private ImageButton btnPointsS1Dec;
    private ImageButton btnPointsS1Add;
    private ImageButton btnPointsS2Dec;
    private ImageButton btnPointsS2Add;
    private ImageButton btnPointsS3Dec;
    private ImageButton btnPointsS3Add;
    private ImageButton btnPointsS4Dec;
    private ImageButton btnPointsS4Add;

    private Switch switchExam;

    private TextView txtPointsAbitur;
    private ImageButton btnPointsAbiturAdd;
    private ImageButton btnPointsAbiturDec;

    private Switch switchHigherLevel;

    private CardView cardViewFinish;
    private AbirechnerBottomSheetDialog main;

    /*
    public AbirechnerBottomSheetDialog newInstance(AbirechnerActivity abirechnerActivity) {
        this.abirechnerActivity = abirechnerActivity;
        return new AbirechnerBottomSheetDialog();
    }
     */

    public AbirechnerBottomSheetDialog(com.hlgkaifu.recyclerview.AbirechnerActivity abirechnerActivity) {
        this.abirechnerActivity = abirechnerActivity;
        this.main = this;
        this.isEdtpage = false;
    }

    public AbirechnerBottomSheetDialog(com.hlgkaifu.recyclerview.AbirechnerActivity abirechnerActivity, int positionAbinot,
                                       com.hlgkaifu.recyclerview.AbiNote abinote){
        this.abirechnerActivity = abirechnerActivity;
        this.main = this;
        this.positionAbinot = positionAbinot;
        this.abinote = abinote;
        this.isEdtpage = true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();

        View v = inflater.inflate(R.layout.abirechner_bottomsheet, container, false);  //stundenplan_bottom_sheet

        headline = v.findViewById(R.id.headline);
        edtTxtCourse = v.findViewById(R.id.edtTxtCourse);
        txtPointsS1 = v.findViewById(R.id.txtPointsS1);
        txtPointsS2 = v.findViewById(R.id.txtPointsS2);
        txtPointsS3 = v.findViewById(R.id.txtPointsS3);
        txtPointsS4 = v.findViewById(R.id.txtPointsS4);
        btnPointsS1Dec = v.findViewById(R.id.btnPointsS1Dec);
        btnPointsS1Add = v.findViewById(R.id.btnPointsS1Add);
        btnPointsS2Dec = v.findViewById(R.id.btnPointsS2Dec);
        btnPointsS2Add = v.findViewById(R.id.btnPointsS2Add);
        btnPointsS3Dec = v.findViewById(R.id.btnPointsS3Dec);
        btnPointsS3Add = v.findViewById(R.id.btnPointsS3Add);
        btnPointsS4Dec = v.findViewById(R.id.btnPointsS4Dec);
        btnPointsS4Add = v.findViewById(R.id.btnPointsS4Add);
        switchExam = v.findViewById(R.id.switchExam);
        txtPointsAbitur = v.findViewById(R.id.txtPointsAbitur);
        btnPointsAbiturAdd = v.findViewById(R.id.btnPointsAbiturAdd);
        btnPointsAbiturDec = v.findViewById(R.id.btnPointsAbiturDec);
        switchHigherLevel = v.findViewById(R.id.switchHigherLevel);
        cardViewFinish = v.findViewById(R.id.cardViewFinish);

        if(isEdtpage){
            headline.setText("Edit Subject");
            edtTxtCourse.setText(abinote.getCourse());
            txtPointsS1.setText("S1: "+ abinote.getPointsSem1()+"P");
            txtPointsS2.setText("S2: "+ abinote.getPointsSem2()+"P");
            txtPointsS3.setText("S3: "+ abinote.getPointsSem3()+"P");
            txtPointsS4.setText("S4: "+ abinote.getPointsSem4()+"P");
            switchExam.setChecked(abinote.isExam());
            switchHigherLevel.setChecked(abinote.isHigherLevel());
        }else{
            headline.setText("New Subject");

        }

        btnPointsS1Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPoint(txtPointsS1, "S1: ");
            }
        });

        btnPointsS1Dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decPoint(txtPointsS1, "S1: ");
            }
        });

        btnPointsS2Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPoint(txtPointsS2, "S2: ");
            }
        });

        btnPointsS2Dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decPoint(txtPointsS2, "S2: ");
            }
        });

        btnPointsS3Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPoint(txtPointsS3, "S3: ");
            }
        });

        btnPointsS3Dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decPoint(txtPointsS3, "S3: ");
            }
        });

        btnPointsS4Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPoint(txtPointsS4, "S4: ");
            }
        });

        btnPointsS4Dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decPoint(txtPointsS4, "S4: ");
            }
        });

        btnPointsAbiturAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPoint(txtPointsAbitur, "Abitur: ");
            }
        });

        btnPointsAbiturDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decPoint(txtPointsAbitur, "Abitur: ");
            }
        });


        cardViewFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isEdtpage){
                    finishEdt();
                }else{
                    finishAdd();
                }

            }
        });

        return v;

    }

    private void finishEdt(){
        com.hlgkaifu.recyclerview.AbiNote abiNote;

        abiNote = new com.hlgkaifu.recyclerview.AbiNote(edtTxtCourse.getText().toString()
                ,getPoints(txtPointsS1, "S1: ")
                ,getPoints(txtPointsS2, "S2: ")
                ,getPoints(txtPointsS3, "S3: ")
                ,getPoints(txtPointsS4, "S4: ")
                ,switchExam.isChecked()
                ,getPoints(txtPointsAbitur, "Abitur: ")
                ,switchHigherLevel.isChecked()
        );
        abirechnerActivity.edtAbinote(positionAbinot, abiNote);
        edtTxtCourse.setText("");
        switchExam.setChecked(false);
        switchHigherLevel.setChecked(false);
        main.dismiss();
    }

    private void finishAdd(){
        com.hlgkaifu.recyclerview.AbiNote abiNote;

        abiNote = new com.hlgkaifu.recyclerview.AbiNote(edtTxtCourse.getText().toString()
                ,getPoints(txtPointsS1, "S1: ")
                ,getPoints(txtPointsS2, "S2: ")
                ,getPoints(txtPointsS3, "S3: ")
                ,getPoints(txtPointsS4, "S4: ")
                ,switchExam.isChecked()
                ,getPoints(txtPointsAbitur, "Abitur: ")
                ,switchHigherLevel.isChecked()
        );
        abirechnerActivity.addAbinote(abiNote);
        edtTxtCourse.setText("");
        switchExam.setChecked(false);
        switchHigherLevel.setChecked(false);
        main.dismiss();
    }

    private void addPoint(TextView textView, String Semester){
        String text = (String) textView.getText();
        text = text.substring(Semester.length(),text.length()-1);
        int points = Integer.parseInt(text);
        if(points < 15) {
            points += 1;
            text = Semester + points + "P";
            textView.setText(text);
        }
    }

    private void decPoint(TextView textView, String Semester){
        String text = (String) textView.getText();
        text = text.substring(Semester.length(),text.length()-1);
        int points = Integer.parseInt(text);
        if(points > 0) {
            points -= 1;
            text = Semester + points + "P";
            textView.setText(text);
        }
    }

    private int getPoints(TextView textView, String Semester){
        String text = (String) textView.getText();
        text = text.substring(Semester.length(),text.length()-1);
        int points = Integer.parseInt(text);
        return points;
    }

}
