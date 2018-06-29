/*
 * This file is part of Bitsquare.
 *
 * Bitsquare is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bitsquare is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bitsquare. If not, see <http://www.gnu.org/licenses/>.
 */

package io.bitsquare.settings;

import io.bitsquare.arbitrator.Arbitrator;
import io.bitsquare.locale.Country;

import org.bitcoinj.utils.MonetaryFormat;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Settings implements Serializable {
    private static final long serialVersionUID = 7995048077355006861L;

    private List<Locale> acceptedLanguageLocales = new ArrayList<>();
    private List<Country> acceptedCountryLocales = new ArrayList<>();
    private List<Arbitrator> acceptedArbitrators = new ArrayList<>();

    private long collateral = 100;  // is 1/1000 so 100 results to 100/1000 = 0,1 (or 10%) 
    // which will be used for multiplying with the amount to get the collateral size in BTC.


    private String btcDenominationString = MonetaryFormat.CODE_BTC;
    final transient StringProperty btcDenomination = new SimpleStringProperty(MonetaryFormat.CODE_BTC);

    ///////////////////////////////////////////////////////////////////////////////////////////
    // Constructor
    ///////////////////////////////////////////////////////////////////////////////////////////

    public Settings() {
    }


    ///////////////////////////////////////////////////////////////////////////////////////////
    // Public API
    ///////////////////////////////////////////////////////////////////////////////////////////

    public void applyPersistedSettings(Settings persistedSettings) {
        if (persistedSettings != null) {
            acceptedLanguageLocales = persistedSettings.getAcceptedLanguageLocales();
            acceptedCountryLocales = persistedSettings.getAcceptedCountries();
            acceptedArbitrators = persistedSettings.getAcceptedArbitrators();
            collateral = persistedSettings.getCollateral();
            setBtcDenomination(persistedSettings.getBtcDenominationString());
        }
    }

    public void addAcceptedLanguageLocale(Locale locale) {
        if (!acceptedLanguageLocales.contains(locale)) {
            acceptedLanguageLocales.add(locale);
        }
    }

    public void removeAcceptedLanguageLocale(Locale item) {
        acceptedLanguageLocales.remove(item);
    }

    public void addAcceptedCountry(Country locale) {
        if (!acceptedCountryLocales.contains(locale)) {
            acceptedCountryLocales.add(locale);
        }
    }

    public void removeAcceptedCountry(Country item) {
        acceptedCountryLocales.remove(item);
    }

    public void addAcceptedArbitrator(Arbitrator arbitrator) {
        if (!acceptedArbitrators.contains(arbitrator)) {
            acceptedArbitrators.add(arbitrator);
        }
    }

    public void removeAcceptedArbitrator(Arbitrator item) {
        acceptedArbitrators.remove(item);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////
    // Setters/Getters
    ///////////////////////////////////////////////////////////////////////////////////////////

    public List<Arbitrator> getAcceptedArbitrators() {
        return acceptedArbitrators;
    }

    public List<Locale> getAcceptedLanguageLocales() {
        return acceptedLanguageLocales;
    }

    public List<Country> getAcceptedCountries() {
        return acceptedCountryLocales;
    }

    public void setCollateral(long collateral) {
        this.collateral = collateral;
    }

    public long getCollateral() {
        return collateral;
    }

    public String getBtcDenomination() {
        return btcDenomination.get();
    }

    public StringProperty btcDenominationProperty() {
        return btcDenomination;
    }

    public void setBtcDenomination(String btcDenomination) {
        btcDenominationString = btcDenomination;
        this.btcDenomination.set(btcDenomination);
    }

    public String getBtcDenominationString() {
        return btcDenominationString;
    }
}