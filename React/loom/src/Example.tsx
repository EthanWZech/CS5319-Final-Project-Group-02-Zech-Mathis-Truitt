import './App.css';

export const Example = () => {
  const thingamajig = (num: number)=> {
    // do stuff
    return num;
  }

  return (
    <div>
        <h1>Hello</h1>
        <div>{thingamajig(3)}</div>
    </div>
  )
};

export default Example;