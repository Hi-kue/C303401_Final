import axios from "axios";
import { Bank } from "@/types/types";

const BASE_URL = "http://127.0.0.1:8080/api/v1/bank";

const bankService = {
    // ANCHOR: FIND METHODS
    findAllBanks: async () => {
        const response = await axios.get(`${BASE_URL}/find/all`);
        console.log(response);

        return response.data.payload;
    },

    findBanksByName: async (bankName: string) => {
        const response = await axios.get(`${BASE_URL}/find/name?bankName=${bankName}`);
        console.log(response.data.payload);

        return response.data.payload;
    },

    findBankById: async (bankId: number) => {
        const response = await axios.get(`${BASE_URL}/find/id?bankId=${bankId}`);
        console.log(response.data.payload);

        return response.data.payload;
    },

    // ANCHOR: CREATE METHOD(S)
    createBank: async (bank: Bank) => {
        const response = await axios.post(`${BASE_URL}/add`, {
            bankName: bank.bankName,
            bankYear: bank.bankYear,
            bankAddress: bank.bankAddress,
            bankAtms: bank.bankAtms,
            bankBranches: bank.bankBranches,
            bankEmployees: bank.bankEmployees,
        }).then((response) => {
            console.log(response);
            return response.data;

        });

        return response.data;
    },

    // ANCHOR: UPDATE METHODS
    updateBankByName: async (bankName: string, bank: Bank) => {
        const response = await axios.put(`${BASE_URL}/find/update/name?bankName=${bankName}`, {
            bankName: bank.bankName,
            bankYear: bank.bankYear,
            bankAddress: bank.bankAddress,
            bankAtms: bank.bankAtms,
            bankBranches: bank.bankBranches,
            bankEmployees: bank.bankEmployees,
        }).then((response) => {
            console.log(response);
            return response.data.payload;

        });

        return response.data.payload;
    },

    updateBankById: async (bankId: number, bank: Bank) => {
        const response = await axios.patch(`${BASE_URL}/find/update/id?bankId=${bankId}`, {
            bankName: bank.bankName,
            bankYear: bank.bankYear,
            bankAddress: bank.bankAddress,
            bankAtms: bank.bankAtms,
            bankBranches: bank.bankBranches,
            bankEmployees: bank.bankEmployees,
        }).then((response) => {
            console.log(response);
            return response.data.payload;

        });

        return response.data.payload;
    },

    // ANCHOR: PATCH METHODS
    patchBankByName: async (bankName: string, bank: Bank) => {
        const response = await axios.patch(`${BASE_URL}/find/patch/name?bankName=${bankName}`, {
            bankName: bank.bankName,
            bankYear: bank.bankYear,
            bankAddress: bank.bankAddress,
            bankAtms: bank.bankAtms,
            bankBranches: bank.bankBranches,
            bankEmployees: bank.bankEmployees,
        }).then((response) => {
            console.log(response.data);
            console.log(response.data.payload);
            console.log(response.data.status);
            return response.data;

        });

        return response.data;
    },

    patchBankById: async (bankId: number, bank: Bank) => {
        const response = await axios.patch(`${BASE_URL}/find/patch/id?bankId=${bankId}`, {
            bankName: bank.bankName,
            bankYear: bank.bankYear,
            bankAddress: bank.bankAddress,
            bankAtms: bank.bankAtms,
            bankBranches: bank.bankBranches,
            bankEmployees: bank.bankEmployees,
        }).then((response) => {
            return response;

        });

        return response;
    },

    // ANCHOR: DELETE METHODS
    deleteBankByName: async (bankName: string) => {
        const response = await axios.delete(`${BASE_URL}/find/delete/name?bankName=${bankName}`);
        return response.data;
    },

    deleteBankById: async (bankId: number) => {
        const response = await axios.delete(`${BASE_URL}/find/delete/id?bankId=${bankId}`);
        return response.data;
    }
};

export default bankService;