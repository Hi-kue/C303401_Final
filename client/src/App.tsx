// Shadcn UI
import { Toaster } from "@/components/ui/toaster";

// Stylesheet(s)
import "./App.css";
import BankDashboard from "./BankDashboard";

function App() {
  return (
    <>  
      <div className="min-h-screen dark">
          <BankDashboard />
          <Toaster />
      </div>
    </>
  )
}

export default App
