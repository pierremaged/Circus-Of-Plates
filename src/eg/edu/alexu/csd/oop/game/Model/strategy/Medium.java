package eg.edu.alexu.csd.oop.game.Model.strategy;

public class Medium implements Strategy {
    @Override
    public int Speed() {
        return 20;
    }

    @Override
    public int ExtraTime() {
        return 2000;
    }

    @Override
    public int Score() {
        return 5;
    }

    @Override
    public int NoOfFallingShapes() {
        return 40;
    }
    
	@Override
	public int maxBombs() {
		
		return 15;
	}


    @Override
    public int MaxHeightOfPlate() {
        return 8;
    }

    @Override
    public int GameTime() {
        return 3*60000;
    }

    @Override
    public int live()
    {
        return 3;
    }

	@Override
	public int maxShapes() {

		return 2;
	}

	@Override
	public int maxShapesColors() {

		return 4;
	}

}
