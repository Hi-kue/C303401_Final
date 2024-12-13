declare module '*.module.css' {
    const classes: { [key: string]: string };
    export default classes;
}

declare interface Employee {
    employeeId?: number;
    employeeName: string;
    employeeDepartment: string;
    createdAt: Date;
    updatedAt: Date;
}

declare interface Bank {
    bankId?: number;
    bankName: string;
    bankYear: number;
    bankAddress: string;
    bankAtms: number;
    bankBranches: number;
    bankEmployees: Array<Employee>;
    createdAt: Date;
    updatedAt: Date;
}
