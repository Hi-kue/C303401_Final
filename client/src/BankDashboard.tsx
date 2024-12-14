import React, { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";

// BankService
import BankService from "@/service/bankService";

// Types
import { Bank } from "@/types/types";

// Components (Shadcn UI)
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Toaster } from "@/components/ui/toaster";
import { useToast } from "@/hooks/use-toast";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle
} from "@/components/ui/card";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger
} from "@/components/ui/alert-dialog";

const bankSchema = z.object({
  bankName: z.string()
    .min(1, "Bank name must be between 1 and 255 characters.")
    .max(255, "Bank name must be between 1 and 255 characters."),
  bankYear: z.coerce.date()
    .refine(date => date < new Date(), "Provided bank year must be in the past."),
  bankEmployees: z.coerce.number()
    .min(1, "Banks must have at least one employee.")
    .max(Number.MAX_SAFE_INTEGER, "Banks cannot exceed the Number.MAX_SAFE_INTEGER threshold."),
  bankAddress: z.string()
    .min(20, "Bank address must be more than 20 and less than 255 characters.")
    .max(255, "Bank address must be more than 20 and less than 255 characters."),
  bankBranches: z.coerce.number()
    .min(1, "Banks must have at least one branch.")
    .max(Number.MAX_SAFE_INTEGER, "Banks cannot exceed the Number.MAX_SAFE_INTEGER threshold."),
  bankATMs: z.coerce.number()
    .min(1, "Banks must have at least one ATM.")
    .max(Number.MAX_SAFE_INTEGER, "Banks cannot exceed the Number.MAX_SAFE_INTEGER threshold."),
});

const BankDashboard: React.FC = () => {
  const [activeTab, setActiveTab] = useState<string>("list");
  const [banks, setBanks] = useState<Bank[]>([]);
  const [selectedBank, setSelectedBank] = useState<Bank | null>(null);
  const { toast } = useToast();

  const createForm = useForm<z.infer<typeof bankSchema>>({
    resolver: zodResolver(bankSchema),
    defaultValues: {
      bankName: "",
      bankYear: new Date(),
      bankEmployees: 0,
      bankAddress: "",
      bankBranches: 0,
      bankATMs: 0,
    },
  });

  const updateForm = useForm<z.infer<typeof bankSchema>>({
    resolver: zodResolver(bankSchema),
    defaultValues: {
      bankName: "",
      bankYear: new Date(),
      bankEmployees: 0,
      bankAddress: "",
      bankBranches: 0,
      bankATMs: 0,
    },
  });

  // ANCHOR: const fetchAllBanks = async ()
  const fetchAllBanks = async () => {
    try {
      const payload = await BankService.findAllBanks();

      if (Array.isArray(payload) && payload.length > 0) {
        setBanks(payload);
        toast({
          style: {
            background: "#22c55e",
            color: "#fff",
          },
          duration: 3000,
          description: "All bank entities were successfully fetched 🎉."
        })

      } else {
        setBanks([]);
        toast({
          variant: "destructive",
          style: {
            background: "#ef4444",
            color: "#fff",
          },
          duration: 3000,
          description: `Error: ${payload.message}`
        });

      }

    } catch (error: any) {
      toast({
        variant: "destructive",
        style: {
          background: "#ef4444",
          color: "#fff",
        },
        duration: 3000,
        description: `Error: ${error.message}`
      });

    }
  };

  // ANCHOR: const onCreateBank = async (values: z.infer<typeof bankSchema>)
  const onCreateBank = async (values: z.infer<typeof bankSchema>) => {
    try {
      const bankData: Partial<Bank> = {
        ...values,
        bankAtms: values.bankATMs,
        bankYear: values.bankYear
      };

      const payload = await BankService.createBank(bankData as Bank);
      if (payload.status === "OK") {
        toast({
          title: "Success ✅",
          description: "Bank entity was successfully added with id: " + payload.data.bankId
        });
        createForm.reset();
        fetchAllBanks();

      } else {
        throw new Error("Failed to add bank entity, something went wrong.");

      }
    } catch (error: any) {
      toast({
        variant: "destructive",
        title: "Error ⚠️",
        description: `error: ${error.message}`
      });

    }
  };

  // ANCHOR: const onUpdateBank = async (values: z.infer<typeof bankSchema>)
  const onUpdateBank = async (values: z.infer<typeof bankSchema>) => {
    if (!selectedBank?.bankId) {
      toast({
        style: {
          background: "#ef4444",
          color: "#fff",
        },
        duration: 3000,
        description: "No bank was selected to update, please select a bank.",
      });
      return;
    }
  
    const bankData: Partial<Bank> = {
      ...values,
      bankAtms: values.bankATMs,
      bankYear: values.bankYear,
    };
  
    try {
      const response = await BankService.patchBankById(selectedBank.bankId, bankData as Bank);
  
      if (response.status === 200 && response.data.status === "OK") {
        toast({
          style: {
            background: "#22c55e",
            color: "#fff",
          },
          duration: 3000,
          description: `Bank with ID: ${selectedBank.bankId} was successfully updated 🎉.`,
        });
  
        updateForm.reset();
        fetchAllBanks();
        setSelectedBank(null);
      } else {
        throw new Error(response.data.message || "Failed to update bank entity.");
      }
    } catch (error: any) {
      toast({
        style: {
          background: "#ef4444",
          color: "#fff",
        },
        duration: 3000,
        description: `Error: ${error.response?.data?.message || error.message}`,
      });
    }
  };
  

  // ANCHOR: const handleDeleteBank = async ()
  const handleDeleteBank = async () => {
    if (!selectedBank?.bankId) return;

    try {
      const response = await BankService.deleteBankById(selectedBank.bankId);
      if (response.status === "OK") {
        toast({
          style: {
            background: "#22c55e",
            color: "#fff",
          },
          duration: 3000,
          description: "Bank entity was successfully deleted 🎉."
        });
        fetchAllBanks();
        setSelectedBank(null);

      } else {
        throw new Error("Failed to delete the bank entity, something went wrong.");

      }
    } catch (error: any) {
      toast({
        style: {
          background: "#ef4444",
          color: "#fff",
        },
        duration: 3000,
        description: `Error: ${error.message}`
      })
    }
  };

  // ANCHOR: const prepareUpdateBank = (bank: Bank)
  const prepareUpdateBank = (bank: Bank) => {
    setSelectedBank(bank);
    updateForm.reset({
      bankName: bank.bankName,
      bankYear: bank.bankYear,
      bankEmployees: bank.bankEmployees,
      bankAddress: bank.bankAddress,
      bankBranches: bank.bankBranches,
      bankATMs: bank.bankAtms,
    });
    setActiveTab("update");
  };

  useEffect(() => {
    fetchAllBanks();
  }, []);

  return (
    <div className="container mx-auto p-4 min-h-screen">
      <header className="text-center">
        <h1 className="text-4xl font-extrabold mb-6 text-white p-4">Bank Management Dashboard</h1>
      </header>

      <Tabs defaultValue="list" value={activeTab} onValueChange={setActiveTab} className="space-y-4">
        <TabsList className="grid w-full grid-cols-3">
          <TabsTrigger value="list">View All Bank Entities</TabsTrigger>
          <TabsTrigger value="create">Add a New Bank Entity</TabsTrigger>
          <TabsTrigger value="update">Update a Bank Entity</TabsTrigger>
        </TabsList>


        {/* ANCHOR: BANK LIST TAB */}
        <TabsContent value="list">
          <Card>
            <CardHeader>
              <CardTitle>Registered Banks</CardTitle>
              <CardDescription>List of all registerd bank entities.</CardDescription>
            </CardHeader>
            <CardContent>
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>Bank Name</TableHead>
                    <TableHead>Established Year</TableHead>
                    <TableHead>Employees</TableHead>
                    <TableHead>Branches</TableHead>
                    <TableHead>Actions</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {banks.map((bank) => (
                    <TableRow key={bank.bankId}>
                      <TableCell>{bank.bankName}</TableCell>
                      <TableCell>{new Date(bank.bankYear).toLocaleDateString()}</TableCell>
                      <TableCell>{bank.bankEmployees}</TableCell>
                      <TableCell>{bank.bankBranches}</TableCell>
                      <TableCell>
                        <Button
                          variant="outline"
                          size="sm"
                          onClick={() => prepareUpdateBank(bank)}
                        >
                          Edit
                        </Button>
                        <AlertDialog>
                          <AlertDialogTrigger asChild>
                            <Button
                              variant="destructive"
                              size="sm"
                              className="ml-2"
                              onClick={() => setSelectedBank(bank)}
                            >
                              Delete
                            </Button>
                          </AlertDialogTrigger>
                          <AlertDialogContent className="bg-gray-800 text-gray-100 shadow-lg border border-gray-700">
                            <AlertDialogHeader>
                              <AlertDialogTitle className="text-white">Delete Bank with bankId: {bank.bankId}</AlertDialogTitle>
                              <AlertDialogDescription className="text-gray-300">
                                Are you sure you want to delete {bank.bankName}? This action is irreversible.
                              </AlertDialogDescription>
                            </AlertDialogHeader>
                            <AlertDialogFooter>
                              <AlertDialogCancel className="bg-black text-gray-300 border-neutral-700 hover:bg-slate-950 hover:text-white">
                                Cancel
                              </AlertDialogCancel>
                              <AlertDialogAction
                                className="bg-red-600 text-white hover:bg-red-700 hover:text-white"
                                onClick={handleDeleteBank}
                              >
                                Continue
                              </AlertDialogAction>
                            </AlertDialogFooter>
                          </AlertDialogContent>
                        </AlertDialog>
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </CardContent>
          </Card>
        </TabsContent>

        {/* ANCHOR: CREATE BANK TAB */}
        <TabsContent value="create">
          <Card>
            <CardHeader>
              <CardTitle>Add New Bank</CardTitle>
              <CardDescription>Register a new bank in the system</CardDescription>
            </CardHeader>
            <CardContent>
              <Form {...createForm}>
                <form onSubmit={createForm.handleSubmit(onCreateBank)} className="space-y-4">
                  <FormField
                    control={createForm.control}
                    name="bankName"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Bank Name</FormLabel>
                        <FormControl>
                          <Input placeholder="Enter a Bank Name" {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={createForm.control}
                    name="bankYear"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Establishment Year</FormLabel>
                        <FormControl>
                          <Input
                            type="date"
                            placeholder="Enter a Value Establishment Year"
                            value={field.value ? field.value.toISOString().split('T')[0] : ""}
                            onChange={(e) => field.onChange(new Date(e.target.value))}
                            name={field.name}
                            ref={field.ref}
                          />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={createForm.control}
                    name="bankEmployees"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Number of Employees</FormLabel>
                        <FormControl>
                          <Input type="number" {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={createForm.control}
                    name="bankAddress"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Bank Address</FormLabel>
                        <FormControl>
                          <Input placeholder="Enter a Bank Address" {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={createForm.control}
                    name="bankBranches"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Number of Branches</FormLabel>
                        <FormControl>
                          <Input type="number" {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <FormField
                    control={createForm.control}
                    name="bankATMs"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Number of ATMs</FormLabel>
                        <FormControl>
                          <Input type="number" {...field} />
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                  <Button type="submit" className="w-full">Add Bank</Button>
                </form>
              </Form>
            </CardContent>
          </Card>
        </TabsContent>

        {/* ANCHOR: UPDATE BANK TAB */}
        <TabsContent value="update">
          <Card>
            <CardHeader>
              <CardTitle>Update Bank</CardTitle>
              <CardDescription>
                {selectedBank
                  ? `
                    [ Id: ${selectedBank.bankId}, BankName: ${selectedBank.bankName} ]
                  `
                  : "Select a bank from bank entities to update."}
              </CardDescription>
            </CardHeader>
            <CardContent>
              {selectedBank ? (
                <Form {...updateForm}>
                  <form onSubmit={updateForm.handleSubmit(onUpdateBank)} className="space-y-4">
                    <FormField
                      control={updateForm.control}
                      name="bankName"
                      render={({ field }) => (
                        <FormItem>
                          <FormLabel>Bank Name</FormLabel>
                          <FormControl>
                            <Input placeholder={`${selectedBank.bankName}`} {...field} />
                          </FormControl>
                          <FormMessage />
                        </FormItem>
                      )}
                    />

                    <FormField
                      control={updateForm.control}
                      name="bankYear"
                      render={({ field }) => (
                        <FormItem>
                          <FormLabel>Establishment Year</FormLabel>
                          <FormControl>
                            <Input
                              type="date"
                              // value={field.value ? field.value.toISOString().split('T')[0] : ""}
                              onChange={(e) => field.onChange(new Date(e.target.value))}
                              name={field.name}
                              ref={field.ref}
                            />
                          </FormControl>
                          <FormMessage />
                        </FormItem>
                      )}
                    />

                    <FormField
                      control={updateForm.control}
                      name="bankEmployees"
                      render={({ field }) => (
                        <FormItem>
                          <FormLabel>Number of Employees</FormLabel>
                          <FormControl>
                            <Input
                              type="number"
                              placeholder={`${selectedBank.bankEmployees}`}
                              {...field}
                            />
                          </FormControl>
                          <FormMessage />
                        </FormItem>
                      )}
                    />

                    <FormField
                      control={updateForm.control}
                      name="bankAddress"
                      render={({ field }) => (
                        <FormItem>
                          <FormLabel>Bank Address</FormLabel>
                          <FormControl>
                            <Input placeholder={`${selectedBank.bankAddress}`} {...field} />
                          </FormControl>
                          <FormMessage />
                        </FormItem>
                      )}
                    />

                    <FormField
                      control={updateForm.control}
                      name="bankBranches"
                      render={({ field }) => (
                        <FormItem>
                          <FormLabel>Number of Branches</FormLabel>
                          <FormControl>
                            <Input
                              type="number"
                              placeholder={`${selectedBank.bankBranches}`}
                              {...field}
                            />
                          </FormControl>
                          <FormMessage />
                        </FormItem>
                      )}
                    />

                    <FormField
                      control={updateForm.control}
                      name="bankATMs"
                      render={({ field }) => (
                        <FormItem>
                          <FormLabel>Number of ATMs</FormLabel>
                          <FormControl>
                            <Input
                              type="number"
                              placeholder={`${selectedBank.bankAtms}`}
                              {...field}
                            />
                          </FormControl>
                          <FormMessage />
                        </FormItem>
                      )}
                    />

                    <Button type="submit" className="w-full">Update Bank</Button>
                  </form>
                </Form>
              ) : (
                <h1 className="text-center text-muted-foreground border-solid border-2 border-slate-400 p-4 rounded-md">
                  Please select a bank from all bank entities to update.
                </h1>
              )}
            </CardContent>

          </Card>
        </TabsContent>
      </Tabs>
      <Toaster />
    </div>
  );
}

export default BankDashboard;