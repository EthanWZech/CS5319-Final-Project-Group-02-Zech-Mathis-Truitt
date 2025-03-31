import './App.css';
import './Example.css'

export const Example = () => {
  const thingamajig = (num: number)=> {
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