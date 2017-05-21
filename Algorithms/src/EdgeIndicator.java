interface EdgeIndicator{
        public boolean edgeIndicator(int u, int v);

        static EdgeIndicator Union(EdgeIndicator g , EdgeIndicator h){
        	EdgeIndicator indicator = new EdgeIndicator(){
        		public boolean edgeIndicator(int u, int v){
        			return g.edgeIndicator(u, v) || h.edgeIndicator(u, v); 
        		}
        	};
        	return indicator;
        }
        
        static EdgeIndicator Intersection(EdgeIndicator g , EdgeIndicator h){
        	EdgeIndicator indicator = new EdgeIndicator(){
        		public boolean edgeIndicator(int u, int v){
        			return g.edgeIndicator(u, v) && h.edgeIndicator(u, v); 
        		}
        	};
        	return indicator;
        }
        
        static EdgeIndicator Reverse(EdgeIndicator g){
        	EdgeIndicator indicator = new EdgeIndicator(){
        		public boolean edgeIndicator(int u, int v){
        			return g.edgeIndicator(v, u); 
        		}
        	};
        	return indicator;
        }
        
        static EdgeIndicator RemoveDirection(EdgeIndicator g){
        	EdgeIndicator indicator = new EdgeIndicator(){
        		public boolean edgeIndicator(int u, int v){
        			return g.edgeIndicator(u, v) || g.edgeIndicator(v, u); 
        		}
        	};
        	return indicator;
        }

        static EdgeIndicator Product(EdgeIndicator graph0 , EdgeIndicator graph1 , int numVertices0, int numVertices1){
        	EdgeIndicator indicator = new EdgeIndicator(){
        		public boolean edgeIndicator(int u, int v){
        			int[] dim = {numVertices0,numVertices1};
        			int u0 = IntegerTuple.Integer2TupleCoordinate(u, dim, 0);
        			int v0 = IntegerTuple.Integer2TupleCoordinate(v, dim, 0);
        			int u1 = IntegerTuple.Integer2TupleCoordinate(u, dim, 1);
        			int v1 = IntegerTuple.Integer2TupleCoordinate(v, dim, 1);
        			if(graph0.edgeIndicator(u0, v0) && u1==v1) return true;
        			if(graph1.edgeIndicator(u1, v1) && u0==v0)return true;
        			return false;
        		}
        	};
        	return indicator;
        }


        
        static EdgeIndicator TensorProduct(EdgeIndicator graph0 , EdgeIndicator graph1 , int numVertices0, int numVertices1){
        	EdgeIndicator indicator = new EdgeIndicator(){
        		public boolean edgeIndicator(int u, int v){
        			int[] dim = {numVertices0,numVertices1};
        			int u0 = IntegerTuple.Integer2TupleCoordinate(u, dim, 0);
        			int v0 = IntegerTuple.Integer2TupleCoordinate(v, dim, 0);
        			int u1 = IntegerTuple.Integer2TupleCoordinate(u, dim, 1);
        			int v1 = IntegerTuple.Integer2TupleCoordinate(v, dim, 1);
        			return graph0.edgeIndicator(u0, v0) && graph1.edgeIndicator(u1,v1) ;
        		}
        	};
        	return indicator;
        }
    }    


