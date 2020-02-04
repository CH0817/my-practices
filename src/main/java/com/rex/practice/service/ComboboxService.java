package com.rex.practice.service;

import com.rex.practice.model.easyui.combobox.ComboboxData;

import java.util.List;

public interface ComboboxService {

    List<ComboboxData> getTradeTypeComboboxData();

    List<ComboboxData> getAccountComboboxData(String userId);

    List<ComboboxData> getItemComboboxData(String userId);

    List<ComboboxData> getAccountTypeComboboxData(String userId);

}
