import './App.css';
import './Example.css'

export const Example = () => {
  const thingamajig = (num: number) => {
    // do stuff
    return num;
  }

  return (
    <div>
        <h1 className='text-blue-500'>Hello</h1>
        <div>{thingamajig(3)}</div>
    </div>
  )
};

export default Example;

//*
//Creating States:
//const [error, setError] = useState("");
//
//Setting the state:
//setError(errorText);
//
//*
//Data Objects:
//export interface ApprovalInfo {
//  approve: boolean;
//  reason: string;
//}
//
//*
//Sharing Information From Other Components:
//type DocToolProps = {
//  loginInfo: LoginInfo;
//}
//
//const DocumentToolDrawer: React.FC<DocToolProps> = ({ loginInfo }) => { }
//
//Then When Calling the Component:
//<DocumentToolDrawer loginInfo={loginInfo} />
//
//*
//Navigation:
//  const navigate = useNavigate();
//  navigate('/home')