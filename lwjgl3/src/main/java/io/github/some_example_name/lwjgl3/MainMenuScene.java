package io.github.some_example_name.lwjgl3;

public class  MainMenuScene extends Scene {
	private CustomButton customButton;

    public MainMenuScene(String name) {
		super(name);
	}

	@Override
    public void create() {
       
        super.create();;
        customButton = new CustomButton("click_me.png", 200.0f, 200.0f, 200.0f, 100.0f);
        
        customButton.setOnClickAction(() -> {
        	this.sceneManager.setScene(GameScene.class);
            
        });
        
        this.entityManager.addEntity(customButton);
        this.ioManager.getInputManager().registerClickable(customButton);

    
    }

 
    @Override
    public void render() {
    	super.render();

    }
    

}
