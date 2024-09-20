package com.example.samplepaint.ui.screens;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.samplepaint.MainActivity;
import com.example.samplepaint.R;
import com.example.samplepaint.databinding.PaintViewFragmentBinding;
import com.example.samplepaint.functions.SaveFile;
import com.example.samplepaint.ui.custome_ui.DrawView;
import com.example.samplepaint.view_models.PaintScreenViewModel;
import com.example.samplepaint.view_models.SingletonViewModelFactory;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

public class PaintViewFragment extends Fragment {

    PaintScreenViewModel paintScreenViewModel;
    DrawView drawingView;
    int currentBrushSize;
    private PaintViewFragmentBinding paintViewFragmentBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        paintViewFragmentBinding = PaintViewFragmentBinding.bind(inflater.inflate(R.layout.paint_view_fragment, container, false));
        paintScreenViewModel =  new ViewModelProvider(this, new SingletonViewModelFactory()).get(PaintScreenViewModel.class);
        drawingView = new DrawView(getContext(), paintScreenViewModel);
        paintViewFragmentBinding.drawFrameBright.addView(drawingView);

        paintViewFragmentBinding.btnUndo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.undoEvent();
            }
        });

        paintViewFragmentBinding.brushSizeSeekbar.setProgress((int) paintScreenViewModel.paintState.getStrokeWidth());
        paintViewFragmentBinding.brushSizeSeekbarText.setText(String.valueOf((int) paintScreenViewModel.paintState.getStrokeWidth()));
        paintViewFragmentBinding.brushSizeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                currentBrushSize = Math.round(((float) i / 5)) * 5;
                //currentBrushSize = i - (i % 5);
                paintViewFragmentBinding.brushSizeSeekbar.setProgress(currentBrushSize);
                paintViewFragmentBinding.brushSizeSeekbarText.setText(String.valueOf(currentBrushSize));

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                paintScreenViewModel.setPaintState(false, getColor(), currentBrushSize);
                drawingView.setPaint(paintScreenViewModel);
            }
        });



        paintViewFragmentBinding.btnUndo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.undoEvent();
            }
        });

        // 保存ボタンを押下
        paintViewFragmentBinding.btnSave1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveFile saveFile = new SaveFile(drawingView, requireContext());
                saveFile.saveBitmapImage();
            }
        });

        // 色選択ボタンを押下
        paintViewFragmentBinding.btnColor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("context is : ", String.valueOf(getContext()));
                ColorPickerDialog.Builder dialog = new ColorPickerDialog.Builder(getContext())
                        .setPreferenceName("MyColorPickerDialog")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("Select", new ColorEnvelopeListener() {
                            @Override
                            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                                //paintScreenViewModel.updatePaint(envelope.getColor(), (int) paintScreenViewModel.paintState.getStrokeWidth());
                                paintScreenViewModel.setPaintState(false, envelope.getColor(), (int) paintScreenViewModel.paintState.getStrokeWidth());
                                drawingView.setPaint(paintScreenViewModel);
                            }
                        })
                        .setTitle("Select Color");
                ColorPickerView colorPickerViewFromDialog = dialog.getColorPickerView();
                dialog.show();

            }
        });

        // SeekBarの選択中の値表示テキストに値追加
        paintViewFragmentBinding.brushSizeSeekbarText.setText(String.valueOf((int) paintScreenViewModel.paintState.getStrokeWidth()));
        paintViewFragmentBinding.brushSizeSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                currentBrushSize = Math.round(((float) i / 5)) * 5;
                //currentBrushSize = i - (i % 5);
                paintViewFragmentBinding.brushSizeSeekbar.setProgress(currentBrushSize);
                paintViewFragmentBinding.brushSizeSeekbarText.setText(String.valueOf(currentBrushSize));

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                paintScreenViewModel.setPaintState(false,getColor(), currentBrushSize);
                drawingView.setPaint(paintScreenViewModel);
            }
        });

        // Brushボタンを押下
        paintViewFragmentBinding.btnBrush1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) requireActivity()).setDefaultColor();
                paintScreenViewModel.paintState = paintScreenViewModel.setPaintState(true, getColor(),10);
                drawingView.setPaint(paintScreenViewModel);
                drawingView.clearFrame();
                paintViewFragmentBinding.brushSizeSeekbar.setProgress(10);

            }
        });
        return paintViewFragmentBinding.getRoot();
    }


    private int getColor() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(requireActivity().getPackageName(),
                Context.MODE_PRIVATE);
        return sharedPreferences.getInt("MyColorPickerDialog_COLOR", 0);
    }
}