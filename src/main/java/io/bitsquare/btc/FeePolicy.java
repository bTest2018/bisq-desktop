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

package io.bitsquare.btc;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeePolicy {
    public static final Coin TX_FEE = Transaction.REFERENCE_DEFAULT_MIN_TX_FEE;

    // The min. REGISTRATION_FEE calculated with Transaction.MIN_NONDUST_OUTPUT would be 0.00015460 which might lead
    // to problems for the spending wallet.
    // Some web wallets don't allow more then 4 decimal places (need more investigation)
    // So we use 0.0002 as that fits also to our 4 decimal places restriction for BTC values.
    // The remaining 0.0000454 BTC is given to miners at the moment as it is lower then dust.
    public static final Coin REGISTRATION_FEE = TX_FEE.add(TX_FEE);

    public static final Coin CREATE_OFFER_FEE = REGISTRATION_FEE; // 0.0002
    public static final Coin TAKE_OFFER_FEE = CREATE_OFFER_FEE;
    private static final Logger log = LoggerFactory.getLogger(FeePolicy.class);

    // those are just dummy yet. trading fees will go probably to arbiters
    // Not used at the moment
    // private static final String registrationFeeAddress = "mvkDXt4QmN4Nq9dRUsRigBCaovde9nLkZR";

    // 
    private static String createOfferFeeAddress;
    private static String takeOfferFeeAddress;

    private final NetworkParameters params;

    @Inject
    public FeePolicy(NetworkParameters params) {
        this.params = params;

        if (params.equals(TestNet3Params.get())) {
            createOfferFeeAddress = "n3LLZ9ngKdxms3gCbHhNPibHTh7DmHigtE";
            takeOfferFeeAddress = "n3LLZ9ngKdxms3gCbHhNPibHTh7DmHigtE";
        }
        else if (params.equals(MainNetParams.get())) {
            // bitsquare donation address used for the moment...
            createOfferFeeAddress = "1BVxNn3T12veSK6DgqwU4Hdn7QHcDDRag7";
            takeOfferFeeAddress = "1BVxNn3T12veSK6DgqwU4Hdn7QHcDDRag7";
        }
        else if (params.equals(RegTestParams.get())) {
            createOfferFeeAddress = "n2upbsaKAe4PD3cc4JfS7UCqPC5oNd7Ckg";
            takeOfferFeeAddress = "n2upbsaKAe4PD3cc4JfS7UCqPC5oNd7Ckg";
        }
    }

    //TODO who is receiver? other users or dev address? use donation option list?
    // Not used at the moment
    // (dev, other users, wikileaks, tor, sub projects (bitcoinj, tomp2p,...)...)
 /*   public Address getAddressForRegistrationFee() {
        try {
            return new Address(params, registrationFeeAddress);
        } catch (AddressFormatException e) {
             e.printStackTrace();
            return null;
        }
    }*/

    //TODO get address form arbitrator list
    public Address getAddressForCreateOfferFee() {
        try {
            return new Address(params, createOfferFeeAddress);
        } catch (AddressFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    //TODO get address form the intersection of  both traders arbitrator lists
    public Address getAddressForTakeOfferFee() {
        try {
            return new Address(params, takeOfferFeeAddress);
        } catch (AddressFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
}