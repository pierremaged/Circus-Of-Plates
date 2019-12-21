package eg.edu.alexu.csd.oop.game.Model.strategy;

public class Hard implements Strategy {
    @Override
    public int Speed() {
        return 10;
    }

    @Override
    public int ExtraTime() {
        return 1000;
    }

    @Override
    public int Score() {
        return 3;
    }

    @Override
    public int NoOfFallingShapes() {
        return 40;
    }
    
	@Override
	public int maxBombs() {
		
		return 20;
	}

    @Override
    public int MaxHeightOfPlate() {
        return 5;
    }

    @Override
    public int GameTime() {
        return 60000;
    }

    @Override
    public int live()
    {
        return 2;
    }

	@Override
	public int maxShapes() {

		return 2;
	}

	@Override
	public int maxShapesColors() {

		return 5;
	}

}
