/**
 * @deprecated: Employee interface is deprecated.
 */
interface Employee {
    employeeId?: number;
    employeeName: string;
    employeeDepartment: string;
    createdAt: Date;
    updatedAt: Date;
}

interface Bank {
    bankId?: number;
    bankName: string;
    bankYear: Date;
    bankAddress: string;
    bankAtms: number;
    bankBranches: number;
    bankEmployees: number;
    createdAt?: Date;
    modifiedAt?: Date;
}


interface ValidationError {
    field: string;
    message: string;
}

interface BackendError {
    errors?: ValidationError[];
    message?: string;
}

export type { Bank, ValidationError, BackendError };