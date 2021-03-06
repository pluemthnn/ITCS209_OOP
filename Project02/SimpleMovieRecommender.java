/**
 * Name: Thanwarat Wongthonngtham
 * ID: 6288145
 * Sec: 3
 */
import java.io.*;
import java.util.*;
import java.util.regex.*;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class SimpleMovieRecommender implements BaseMovieRecommender {
	public  Map<Integer, Movie> Movies = new HashMap<>();
	public  Map<Integer, User> Users = new HashMap<>();	
/**
 *  loadMovie   
 */
	@Override
	public Map<Integer, Movie> loadMovies(String movieFilename) {
		// TODO Auto-generated method stub
		Map<Integer, Movie> loadMovies = new HashMap<>();

		BufferedReader reader = null;
		File file = new File(movieFilename);
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			Pattern pattern = Pattern.compile("(\\d+),\\\"?(.+) \\((\\d+)\\)\\\"?,(.+)");

			while((line = reader.readLine()) != null)
			{	
				//if(line.isEmpty()) continue;
				Matcher check = pattern.matcher(line);

				if(check.find()) {
					int id = Integer.parseInt(check.group(1));
					String title = check.group(2);
					int year = Integer.parseInt(check.group(3));
					String tags = check.group(4);

					Movie a = new Movie(id,title,year);
					loadMovies.put(id, a);

					String[] tag = tags.split("\\|");
					for(String s : tag) {
						a.addTag(s);
					}
					
				}

			}

			reader.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return loadMovies;
	}
/**
 * 
 *  loadUsers
 *  @param loadUsers
 */
	@Override
	public Map<Integer, User> loadUsers(String ratingFilename) {
		// TODO Auto-generated method stub
		Map<Integer, User> loadUsers = new HashMap<>();

		BufferedReader reader = null;
		File file = new File(ratingFilename);
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			Pattern pattern = Pattern.compile("(\\d+)");

			while((line = reader.readLine()) != null)
			{	

				if(line.isEmpty()) continue;
				String[] User = line.split(",");
				Matcher check = pattern.matcher(line);

				if(check.find()) {
					int uid = Integer.parseInt(User[0]);
					int mid = Integer.parseInt(User[1]);

					if(loadUsers.get(uid) == null) {

						double rating = Double.parseDouble(User[2]);
						long timestamp = Long.parseLong(User[3]);
						Movie movie = Movies.get(mid);

						User a = new User(uid);
						a.addRating(movie, rating, timestamp);
						loadUsers.put(uid, a);


					}
					else {
						double rating = Double.parseDouble(User[2]);
						long timestamp = Long.parseLong(User[3]);
						Movie movie = Movies.get(mid);

						loadUsers.get(uid).addRating(movie,rating,timestamp);
					}

				}

			}
			
			reader.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return loadUsers;

	}
/**
 * 
 *  calling loadMovie(),loadUsers()
 *  
 *  @param Movies,Users
 */
	@Override
	public void loadData(String movieFilename, String userFilename) {
		Movies = loadMovies(movieFilename);
		Users = loadUsers(userFilename);
	}

	@Override
	public Map<Integer, Movie> getAllMovies() {
		if(!Movies.equals(null)) {
			return Movies;
		}
		return null;
	}

	@Override
	public Map<Integer, User> getAllUsers() {
		if(!Users.equals(null)) {
			return Users;
		}
		return null;
	}
/**
 * 
 * @param 
 */
	@Override
	public void trainModel(String modelFilename) {
		// TODO Auto-generated method stub
		Set<Integer> Keymovie = new TreeSet<>(Movies.keySet());	
	//-------------------Write File--------------------//
		FileWriter w = null;
		try {
			w = new FileWriter(modelFilename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	//------------------Build String------------------//
		StringBuilder String = new StringBuilder();
		String.append("@NUM_USER " + Users.size() + "\n");

		Map<Integer,Integer> usermap = new LinkedHashMap<>();
		int i = 0;
		for(User values : Users.values()) {
			usermap.put(i, values.uid);
			i++;
		}
		String.append("@USER_MAP " + usermap + "\n");

		String.append("@NUM_MOVIES " + Movies.size() + "\n");

		Map<Integer,Integer> moviemap = new LinkedHashMap<>();
		i = 0;
		for(Integer values : Keymovie) {
			moviemap.put(i, values);
			i++;
		}
		String.append("@MOVIES_MAP " + moviemap + "\n");
		System.out.println("@@@ Computinng user rating matrix");
		String.append("@RATING_MATRIX " + "\n");
		for(i = 0; i < Users.size(); i++) {
			int getuser = usermap.get(i);
			
			for(int j = 0; j < Movies.size(); j++) {
				int getmovie = moviemap.get(j);
				
				if(Users.get(getuser).ratings.get(getmovie) == null) {
					String.append("0.0 ");
				}
				else {
					String.append(Users.get(getuser).ratings.get(getmovie).rating + " ");
				}
				
			}
			
			String.append(Users.get(getuser).getMeanRating());
			String.append("\n");
		}
		
	/**----------Similarity-------------*/
		System.out.println("@@@ Computinng user sim matrix");
		String.append("@USERSIM_MATRIX " + "\n");
		
		for(User u : Users.values()) {
			for(User v : Users.values()) {
				if(u.uid == v.uid) {
					String.append("1.0 ");
				}
				else {
					Set<Integer> ratedMovies = new HashSet<>(Users.get(u.uid).ratings.keySet());
					ratedMovies.retainAll(Users.get(v.uid).ratings.keySet());
					
		 			double up = 0.0, udown = 0.0, vdown = 0.0;
					double meanrateU = u.getMeanRating();
					double meanrateV = v.getMeanRating();
					
					for(int r : ratedMovies) {
						double Ur = u.ratings.get(r).rating;
						double Vr = v.ratings.get(r).rating;
						
						up += ((Ur - meanrateU)*(Vr - meanrateV));
						udown += Math.pow(Ur - meanrateU, 2);
						vdown += Math.pow(Vr - meanrateV, 2);
					}
					double down = (Math.sqrt(udown))*(Math.sqrt(vdown));
					double updown = up/down;
					if(down == 0) {
						String.append("0.0 ");
					}
					else {
						String.append(updown + " ");
					}
				}
				
			}
			
			String.append("\n");
		}
		
		System.out.println("@@@ Writing out model file");		
	/**------Write String------*/
	        BufferedWriter bw = new BufferedWriter(w);
	        try {
	        	bw.write(String.toString());
	            bw.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
    }

/**
 * 
 * 
 * 
 */
	public BiMap<Integer ,Integer> UserMap = HashBiMap.create();
	public BiMap<Integer ,Integer> MovieMap = HashBiMap.create();
	public double[][] Ratematrix;
	public double[][] Simmatrix;

	@Override
	public void loadModel(String modelFilename){
		Ratematrix = new double[Users.size()][Movies.size()+1];
		Simmatrix = new double[Users.size()][Users.size()];
		int count = 0, count2 = 0;
		BufferedReader reader = null;
		File file = new File(modelFilename);
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			
			Pattern pattern1 = Pattern.compile("(@USER_MAP) (\\{)(.*)(\\})"); 
			Pattern pattern2 = Pattern.compile("(@MOVIES_MAP) (\\{)(.*)(\\})");
			Pattern pattern2_5 = Pattern.compile("(@MOVIE_MAP) (\\{)(.*)(\\})"); 
			Pattern pattern3 = Pattern.compile("(\\d+.\\d+\\s)");
			
			while((line = reader.readLine()) != null)
			{	
				
				Pattern pattern = Pattern.compile("(\\d+)=(\\d+)");
				Matcher check = pattern.matcher(line);
				
				Matcher match1 = pattern1.matcher(line);
				Matcher match2 = pattern2.matcher(line);
				Matcher match2_5 = pattern2_5.matcher(line);
				Matcher match3 = pattern3.matcher(line);

				if(match1.find() || match2.find() || match2_5.find()) {	
					if(line.contains("@USER_MAP")) {
						while(check.find()) {
							
							int key = Integer.parseInt(check.group(1));
							int value = Integer.parseInt(check.group(2));
							UserMap.put(key, value);
						}						
					}
					if(line.contains("@MOVIE_MAP") || line.contains("@MOVIES_MAP")) {
						while(check.find()) {
							int key = Integer.parseInt(check.group(1));
							int value = Integer.parseInt(check.group(2));
							
							MovieMap.put(key, value);
						}
					}
				}
				else if(match3.find()) {
					String[] each = line.split(" ");
					//@RatingMatrix
					if(each.length == MovieMap.size()+1) {
						for (int k = 0; k < each.length; k++) {    
                            Ratematrix[count][k] = Double.parseDouble(each[k]);
                           
						}
						count++;
					}
					if(each.length == UserMap.size()) {
						for (int k = 0; k < each.length; k++) {        
                            Simmatrix[count2][k] = Double.parseDouble(each[k]);
                           
						}
						count2++;
					}
				}

			}
			
			reader.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public double predict(Movie m, User u) {
		double predicted = 0.0;
		BiMap<Integer,Integer> inUserMap = UserMap.inverse();
		BiMap<Integer,Integer> inMovieMap = MovieMap.inverse();
		if(!UserMap.containsValue(u.uid)) {
			predicted = u.getMeanRating();
		}
		else {
			int Upos = inUserMap.get(u.uid);
			int Mpos = inMovieMap.get(m.mid);
			double Up = 0.0;
			double Down = 0.0;
			for(int k = 0; k < Ratematrix.length; k++) {
				if(k == Upos) continue;
				if(Ratematrix[k][Mpos] != 0.0) {
//					double U = Ratematrix[k][Mpos];
//					double Ui = Ratematrix[k][Ratematrix[k].length-1];
					double sim = Simmatrix[k][Upos];
                    Up += sim * (Ratematrix[k][Mpos] - Ratematrix[k][Ratematrix[k].length-1]);
                    Down += Math.abs(sim);
                    }
			}
			predicted = Ratematrix[Upos][Ratematrix[Upos].length-1] + (Up/Down);
			if(Up == 0.0 || Down == 0.0) {
				predicted = Ratematrix[Upos][Ratematrix[Upos].length-1];
			}
	        
	        if(predicted > 5.0) {
	        	predicted = 5.0;
	        }
			else if(predicted < 0.5) {
				predicted = 0.5;
			} 
	        return predicted;
		}

		return 0;
	}

	@Override
	public List<MovieItem> recommend(User u, int fromYear, int toYear, int K) {
		 List<MovieItem> list = new ArrayList<MovieItem>();
		 for(Movie m : Movies.values()) {
			 if(m.year >= fromYear && m.year <= toYear){
                 MovieItem item = new MovieItem(m, predict(m,u));
                 list.add(item);
			 }
		 }
			 Collections.sort(list);                                     
	            if(list.size() < K) {
	            	return list;                           
	            }
	            else {
	            	return list.subList(0, K); 
	            }
	 }
}