package valoeghese.fabricpy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import jep.SharedInterpreter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.LanguageAdapter;
import net.fabricmc.loader.api.LanguageAdapterException;
import net.fabricmc.loader.api.ModContainer;

public class PythonLangugeAdapter implements LanguageAdapter {
	@SuppressWarnings("unchecked")
	@Override
	public <T> T create(ModContainer mod, String value, Class<T> type) throws LanguageAdapterException {
		try {
			if (type == ModInitializer.class) {
				String code = loadAsString(value.replace('.', '/'));
				ModInitializer result = () -> new SharedInterpreter().exec(code);
				return (T) result;
			} else {
				throw new LanguageAdapterException("Unable to handle type " + type.getName());
			}
		} catch (IOException e) {
			throw new LanguageAdapterException("IOException loading python resource", e);
		}
	}

	private static InputStream load(String location) {
		return PythonLangugeAdapter.class.getClassLoader().getResourceAsStream(location);
	}

	private static String loadAsString(String location) throws IOException {
		InputStream is = load(location);
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nBytesRead;
		byte[] bufferBuffer = new byte[0x4000];

		while ((nBytesRead = is.read(bufferBuffer, 0, bufferBuffer.length)) != -1) {
			buffer.write(bufferBuffer, 0, nBytesRead);
		}

		is.close();
		return new String(buffer.toByteArray(), StandardCharsets.UTF_8);
	}
}
