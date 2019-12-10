package com.rex.my.business.service;

import com.rex.my.model.easyui.combobox.ComboboxData;

import java.util.List;

public interface ComboboxService {

    List<ComboboxData> getTradeTypeComboboxData();

    List<ComboboxData> getAccountComboboxData();

    List<ComboboxData> getItemComboboxData();

}
