import Component from "./core/component";

class App extends Component<any> {
    template(): InnerHTML["innerHTML"] {
        return `
        Hello World
        `;
    }
}

export default App;