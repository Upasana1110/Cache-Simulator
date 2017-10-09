import java.util.Random;
import java.util.Scanner;

/*
 * fullyAssociativeBlockPlacement.java
 * 
 * This is a Cache Simulator for Fully Associative Block Placement Strategy
 */
public class fullyAssociativeBlockPlacement{


	static int readMiss = 0, readHits = 0, writeMiss = 0, writeHits = 0;
	static int[][] memory,cache;	
	static Scanner scan= new Scanner(System.in);	

	public static void main(String[] args){
		String choice;

		memory = initMem();
		cache =  init_cache();
		printCachePerformance();
		print_mem(memory.length,memory[0].length);
		print_cache(cache);
		printCachePerformance();
		print_mem(memory.length,memory[0].length);
		print_cache(cache);
		System.out.println("---------------------------------- FULLY ASSOCIATIVE BLOCK PLACEMENT STRATEGY ----------------------------------");

		while(true){	
			System.out.println("Enter choice:");
			System.out.println("1. Read");
			System.out.println("2. Write");
			System.out.println("Press Q to Quit");


			choice = scan.nextLine();
			System.out.println("Your Choice: "+choice);
			if(choice.equals("1"))
			{
				System.out.println("READING FROM MEMORY");
				load_from_mem(0,0);
				printCachePerformance();
				print_mem(memory.length,memory[0].length);
				print_cache(cache);
			}
			else if(choice.equals("2")) {
				System.out.println("WRITING TO MEMORY");
				write_through_to_mem(0,0,0);
				printCachePerformance();
				print_mem(memory.length,memory[0].length);
				print_cache(cache);
			}
			else if (choice.equals("Q")||choice.equals("q")) {
				System.out.println("Quitting");
				System.out.println("Exiting Main Loop");
				break;
			}
			else {
				System.out.println(" ");
			}  
		}

	}

	/*
	 *  This function intializes the Memory Block
	 */
	static int[][] initMem()	{
		int memory[][] =  {
				{	 0,	1,	2,	3,	4,	5,	6,	7,	8,	9,	10,	11,	12,	13,	14,	15	}, 
				{	10,	11,	12,	13,	14,	15,	16,	17,	18,	19,	20,	21,	22,	23,	24,	25	}, 
				{	20,	21,	22,	23,	24,	25,	26,	27,	28,	29,	30,	31,	32,	33,	34,	35	}, 
				{	30,	31,	32,	33,	34,	35,	36,	37,	38,	39,	40,	41,	42,	43,	44,	45	}, 
				{	40,	41,	42,	43,	44,	45,	46,	47,	48,	49,	50,	51,	52,	53,	54,	55	}, 
				{	50,	51,	52,	53,	54,	55,	56,	57,	58,	59,	60,	61,	62,	63,	64,	65	}, 
				{	60,	61,	62,	63,	64,	65,	66,	67,	68,	69,	70,	71,	72,	73,	74,	75	}, 
				{	70,	71,	72,	73,	74,	75,	76,	77,	78,	79,	80,	81,	82,	83,	84,	85	}, 
				{	80,	81,	82,	83,	84,	85,	86,	87,	88,	89,	90,	91,	92,	93,	94,	95	}, 
				{	90,	91,	92,	93,	94,	95,	96,	97,	98,	99,	100,	101,	102,	103,	104,	105	}, 
				{	100,  101,	102,	103,	104,	105,	106,	107,	108,	109,	110,	111,	112,	113,	114,	115	}, 
				{	110,	111,	112,	113,	114,	115,	116,	117,	118,	119,	120,	121,	122,	123,	124,	125	}, 
				{	120,	121,	122,	123,	124,	125,	126,	127,	128,	129,	130,	131,	132,	133,	134,	135	}, 
				{	130,	131,	132,	133,	134,	135,	136,	137,	138,	139,	140,	141,	142,	143,	144,	145	}, 
				{	140,	141,	142,	143,	144,	145,	146,	147,	148,	149,	150,	151,	152,	153,	154,	155	}, 
				{	150,	151,	152,	153,	154,	155,	156,	157,	158,	159,	160,	161,	162,	163,	164,	165	}
		};
		return memory;
	}

	/*
	 * This function initializes the cache.
	 */
	static int[][] init_cache(){
		int cache[][] =  {
				// Set,Valid,Tag,...
				{	0,	0,	-99,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0	},
				{	0,	0,	-99,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0	},
				{	1,	0,	-99,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0	},
				{	1,	0,	-99,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0   },
				{	2,	0,	-99,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0	},
				{	2,	0,	-99,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0	},
				{	3,	0,	-99,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0	},
				{	3,	0,	-99,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0	}
		};
		return cache;
	}

	/*
	 *  This function receives the memory block number of interest and calculates 
	 *  the cache line number associated with is using the formula for 2-Way Set Associative caches.
	 *  Please note that internal to this function, you determine the SET NUMBER of the interest. 
	 *  Then within that set you search for the first cache line whose valid bit is 0
	 *  This cache line number then will be returned by this function.
	 * 
	 *  If no cache lines with valid bit equal to 0 were found, then randomly select one 
	 *  of the cache lines to be replaced.
	 */
	static int map_mem_block_to_cacheline_for_replacement(int blockNo){
		
		blockNo = blockNo * 2 + new Random().nextInt(1);
		for (int temp=0;temp<cache.length;temp++){
			
		if 	(cache[temp][2]==blockNo){
			return temp;
		}
//		
//		else if(cache[temp][1]== 0 ){
//				return temp;
//			}
		}

		return (new Random()).nextInt(cache.length-1);
	}

	/* This function loads a block from memory  into the appropriate cache line 
	 * (as per the Set associative cache formula). It also sets the valid bit for this cache line to 1, 
	 *  and copies the block number of the memory into the tag field.
	 */
	static void load_from_mem(int block_num, int block_size)
	{
		String block,offSetNo;
		readMiss++;
		System.out.println("Enter BlockNo.:");
		block = scan.nextLine();	
		block_num = Integer.parseInt(block);

		System.out.println("Enter offSetNo:");
		offSetNo = scan.nextLine();
		block_size = Integer.parseInt(offSetNo);
		System.out.println("get_cpu_action(); action = READ, block :"+block+", offset :"+offSetNo);
		System.out.println("cache read miss block ["+block+"] offset["+offSetNo+"] --> ["+0+"]");

		int offSettemp = map_mem_block_to_cacheline_for_replacement(block_size);
//		System.out.println("Cache Length is:"+cache[0].length-3);
		for(int i=0;i<cache[0].length-3;i++){
			cache[offSettemp][i + 3] = memory[block_num][i];
		}
		cache[offSettemp][2] = block_num; // Setting tag as block number
		cache[offSettemp][1] = 1; // Setting valid flag as 1

	}
	/*
	 * Gets a mem block number and determines if that block is already 
	 * in cache memory block number has its tag equal to the block number
	 */
	static int is_cache_hit(int block_num){
		for (int temp=0;temp<cache.length;temp++){
			if (cache[temp][2]==block_num){
				return temp;
			}
		}
		return -1;
	}
	/*
	 * Stores a single value to the given offset of a given block in memory
	 */
	static void store_to_mem(int block_num, int offset, int value){
		memory[block_num][offset] = value;
	}
	
	/*
	 * This function writes an entire cache line to the appropriate block in the memory associated with it.
	 */
	static void write_through_to_mem(int block_num, int block_size, int value)
	{
		String block,offSetNo,enteredvalue;

		System.out.println("Enter BlockNo :");
		block = scan.nextLine();	
		block_num = Integer.parseInt(block);
		System.out.println("Enter offSetNo :");
		offSetNo = scan.nextLine();
		block_size = Integer.parseInt(offSetNo);
		System.out.println("Enter Value :");
		enteredvalue = scan.nextLine();
		value = Integer.parseInt(enteredvalue);

		int result=is_cache_hit(block_num);
		System.out.println("get_cpu_action(); action = WRITE, block :"+block+", offset :"+offSetNo+", value :"+enteredvalue);
		if (!(result<0)){
			store_to_mem(block_num,block_size,value);
			
			writeHits++;
		}
		else{	
			System.out.println("cache write miss block ["+block+"] offset["+offSetNo+"] --> ["+0+"]");
			writeMiss++;
			store_to_mem(block_num,block_size,value);
		}
	}
	
	/*
	 * This function calculates the number of sets in this cache based on the associativity given.
	 */
	static int calc_number_of_sets(int associativity){
		return (int)((cache[0].length-3)/associativity);
	}
	
	/*
	 * this function calculates the set number that any cache line belongs to
	 */
	static int calc_my_set_number(int cache_line_num, int associativity){
		return (int)(cache_line_num/associativity);
	}
	
	/*
	 * This function prints all current contents of memory.
	 */
	static void print_mem(int num_cache_lines, int cache_line_size)
	{
		System.out.println("*****************************PRINTING MEMORY*****************************");
		for (int i = 0; i < num_cache_lines; i++ )
		{
			for (int j = 0; j < cache_line_size; j++)
			{	
				System.out.print(memory[i][j] + "  ");
			}
			System.out.println();

		}
		System.out.println("*****************************END OF MEMORY*******************************");
	}
	
	/*
	 * based on associativity returns the set number in cache mapped to a mem block number
	 */
	int map_mem_block_to_set(int block_num, int associativity) {
		return calc_my_set_number(map_mem_block_to_cacheline_for_replacement(block_num),associativity);
	}
	
	/*
	 * Prints the current hits and misses
	 */
	static void printCachePerformance(){
		System.out.println("**************************PRINTING CACHE PERFORMANCE**************************");
		System.out.println("Number of Read Misses: "+readMiss);
		System.out.println("Number of Read Hits: "+readHits);
		System.out.println("Number of Write Misses : "+writeMiss);
		System.out.println("Number of Write Hits: "+writeHits);
		System.out.println("**************************END OF CACHE PERFORMANCE************************");
	}

	
	


	/*
	 * This function prints all current contents of cache.
	 */
	static void print_cache(int my_cache[][]){
		System.out.println("*****************************PRINTING CACHE*****************************");

		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 19; j++)
			{
				String prefix = "";

				if (j == 0)
				{
					prefix = "Set = ";
				}
				else if (j == 1)
				{
					prefix = "V = ";
				}
				else if (j == 2)
				{
					prefix = "Tag = ";
				}
				System.out.print(prefix + cache[i][j] + "  ");
			}
			System.out.println("");
		}
		System.out.println("******************************END OF CACHE******************************");
	}


}
