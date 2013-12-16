package net.tomoyamkung.library;

import java.util.ArrayList;
import java.util.List;

import net.tomoyamkung.library.util.ListUtil;
import net.tomoyamkung.library.util.StringUtil;

/**
 * ImageMagick の実行コマンドパスとコマンドパラメータを保持するクラス。
 * 
 * @author tomoyamkung
 * 
 */
public class Command {

	/**
	 * 実行するコマンドのパス。
	 */
	private String commandPath;

	/**
	 * 実行するコマンドのパラメータ。
	 */
	private List<String> parameters;

	/**
	 * 実行するコマンドのパスを設定する。
	 * 
	 * @param commandPath
	 *            実行するコマンドのパス
	 */
	public Command(String commandPath) {
		this.commandPath = commandPath;

		parameters = new ArrayList<String>();
	}

	/**
	 * 実行するコマンドのパラメータを追加する。
	 * 
	 * @param parameter
	 *            パラメータ
	 * @return
	 */
	public Command addParameter(String parameter) {
		parameters.add(parameter);

		return this;
	}

	/**
	 * 妥当性を確認する。
	 * 
	 * 次の条件に当てはまる場合は不適切と見なし <code>IllegalArgumentException</code> を生成する。
	 * 
	 * <ul>
	 * <li>コマンドパスが null である</li>
	 * <li>コマンドパスがブランクである</li>
	 * <li>パラメータが null である</li>
	 * <li>パラメータが空である</li>
	 * </ul>
	 */
	public void validate() {
		if(StringUtil.isNullOrEmpty(commandPath)) {
			throw new IllegalArgumentException(
					"commandPath may not be specified.");
		}
		
		if(ListUtil.isNullOrEmpty(parameters)) {
			throw new IllegalArgumentException(
					"parameters may not be specified.");
		}
	}

	/**
	 * ImageMagick の実行コマンドを取得する。
	 * 
	 * @return
	 */
	public List<String> getCommand() {
		parameters.add(0, commandPath);
		return parameters;
	}

	@Override
	public String toString() {
		return String.format("commandPath:%s, parameters:%s", commandPath,
				parameters.toString());
	}

}
