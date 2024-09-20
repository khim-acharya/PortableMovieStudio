package com.example.samplepaint.entities;

class UIState{
    public UIState(Boolean undoBtn, Boolean saveBtn, Boolean colorBtn, Boolean brushBtn) {
        this.undoBtn = undoBtn;
        this.saveBtn = saveBtn;
        this.colorBtn = colorBtn;
        this.brushBtn = brushBtn;
    }

    Boolean undoBtn, saveBtn, colorBtn, brushBtn;
}
