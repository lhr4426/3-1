package com.example.mc_week7_prac2.ui.slideshow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mc_week7_prac2.R;
import com.example.mc_week7_prac2.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        final String[] versionArray = new String[]{"마시멜로", "누가", "오레오"};

        AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
        //  AlertDialog dlg = builder.create();

        dlg.setTitle("좋아하는 버전은?");
        dlg.setIcon(R.mipmap.ic_launcher);


//        dlg.setItems(versionArray,
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        textView.setText(versionArray[which]);
//                    }
//                });


//
//        dlg.setSingleChoiceItems(versionArray, -1,   new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                //textView.setText(versionArray[which]);
//                slideshowViewModel.setMyText(versionArray[which]);
//
//                dialog.cancel();  // gabriel
//
//            }
//        });




//        dlg.setMultiChoiceItems(versionArray, new boolean[]{true, false, false},  new DialogInterface.OnMultiChoiceClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
//                    if (b) textView.setText(versionArray[i]+" selected");
//            }
//        });

        View dialogView;

        dialogView = (View) View.inflate(getActivity(), R.layout.dialogview, null);
        dlg.setTitle("사용자 입력");
        dlg.setIcon(R.mipmap.ic_launcher);
        // dialogView = (View) View.inflate(getActivity(), R.layout.dialogview_homework, null);


        // 직접 만든 xml 뷰를 dialog에 넣을 수 있음
        dlg.setView(dialogView);

        // OK 설정
        dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                EditText name = dialogView.findViewById(R.id.editText);
                textView.setText(name.getText().toString());
            }
        });

        // Cancel 설정
        dlg.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(getActivity(), "cancel action", Toast.LENGTH_SHORT).show();

            }
        });

        dlg.show();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}